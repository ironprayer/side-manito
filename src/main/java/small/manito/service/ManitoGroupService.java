package small.manito.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import small.manito.querydsl.dto.GroupDTO;
import small.manito.querydsl.dto.GroupMappingDTO;
import small.manito.querydsl.entity.Invite;
import small.manito.querydsl.entity.ManitoGroup;
import small.manito.querydsl.entity.ManitoMapping;
import small.manito.querydsl.entity.User;
import small.manito.repository.InviteRepository;
import small.manito.repository.ManitoGroupRepository;
import small.manito.repository.ManitoMappingRepository;
import small.manito.repository.UserRepository;
import small.manito.type.InviteAnswer;
import small.manito.type.InviteAnswerStatus;
import small.manito.type.ManitoStatus;

import java.util.List;

@Getter
@Service
@RequiredArgsConstructor
public class ManitoGroupService {

    private final ManitoGroupRepository manitoGroupRepository;
    private final ManitoMappingRepository manitoMappingRepository;
    private final InviteRepository inviteRepository;
    private final UserRepository userRepository;

    public void create(ManitoGroup manitoGroup, Long userId){
        manitoGroup.changeStatus(ManitoStatus.WAITING);
        var saveManitoGroup = manitoGroupRepository.save(manitoGroup);
        manitoMappingRepository.save(ManitoMapping.builder()
                .manitoGroup(saveManitoGroup)
                .user(User.builder().id(userId).build())
                .build());
    }

    @Transactional
    public void answerInvited(InviteAnswer inviteAnswer){
        if(inviteAnswer.getIsAccept()){
            acceptJoin(inviteAnswer);
        }else{
            rejectJoin(inviteAnswer);
        }
    }

    private void acceptJoin(InviteAnswer inviteAnswer){
        var invitedMapping = inviteRepository.findByManitoGroupAndGuest(
                ManitoGroup.builder()
                        .id(inviteAnswer.getGroupId())
                        .build(),
                User.builder()
                        .id(inviteAnswer.getUserId())
                        .build()
        ).get();

        if(!(invitedMapping == null)){
            invitedMapping.changeStatus(InviteAnswerStatus.ACCEPT);
            manitoMappingRepository.save(ManitoMapping
                    .builder()
                    .manitoGroup(invitedMapping.getManitoGroup())
                    .user(invitedMapping.getGuest())
                    .build()
            );
        }
    }

    private void rejectJoin(InviteAnswer inviteAnswer){
        var invitedMapping = inviteRepository.findByManitoGroupAndGuest(
                ManitoGroup.builder()
                        .id(inviteAnswer.getGroupId())
                        .build(),
                User.builder()
                        .id(inviteAnswer.getUserId())
                        .build()
        ).get();

        invitedMapping.changeStatus(InviteAnswerStatus.REJECT);
    }

    @Transactional
    public void start(String adminId, Long groupId) {
        //검증 단계 필요 (adminId의 group 권한) -> 권한 없으면 Exception 처리
        var manitoGroup = manitoGroupRepository.findById(groupId).get();

        if(manitoGroup.isFull()) manitoGroup.changeStatus(ManitoStatus.ONGOING);
    }

    @Transactional
    public void inviteUser(Long groupId, Long hostId, String guestId){
        var manitoGroup = manitoGroupRepository.findById(groupId).get();
        var guest = userRepository.findByUserId(guestId).get();

        if(!manitoGroup.isFull()) {
            inviteRepository.save(
                    Invite.builder()
                            .manitoGroup(manitoGroup)
                            .hostId(hostId)
                            .guest(guest)
                            .status(InviteAnswerStatus.PENDING)
                            .build()
            );
        }
    }

//    @Transactional
//    public void matchingManito(Long groupId){
//        var members = manitoMappingRepository.findAllByGroupId(groupId);
//        shuffleMember(members);
//    }

    @Transactional
    public List<GroupDTO> getManitoGroupWithStatus(Long userId, ManitoStatus status){
        return manitoMappingRepository.findGroupsWithUserIdAndStatus(userId, status);
    }

    @Transactional
    public User getUser(Long userId){
        return userRepository.findById(userId).get();
    }

    public List<GroupDTO> getManitoGroupInvited(Long userId){
        return inviteRepository
                .findGroupsInGuestId(userId).stream().map((invite -> {
                    return invite.toGroupDTO();
                }))
                .toList();
    }

    private void shuffleMember(List<ManitoMapping> members){
        var count = members.size();

        // 매칭하는 곳 ( 이후에 필요하면 더 랜덤하게 넣는 알고리즘을 짜야할듯 )
        for(var index = 0; index < count; index++){
            var manitoId = members.get((index + 1) % count).getUser().getId();
            members.get(index).setManitoId(manitoId);
        }
    }

    // 별도로 빠져있는 것이 좋다 -> package 하나 만들자
    @Scheduled(fixedDelay = 1000 * 60)
    @Transactional
    public void end(){
        manitoGroupRepository.endOfAllMantioGroup();
        // flush가 자동으로 되었던가? 아니면 flush를 시켜줘야 했던가? 해야한다면 방법은?
    }

    // manito 맞췄는지 여부를 Back-End쪽에서 가지고 있을 필요가 있을까? ( 어드민 유저가 보고싶다면 ? )
    // 여기에 queryDSL 사용해보면 되겠는데
    public GroupMappingDTO getManitoResult(Long groupId, Long userId, String manitoName ) {
        return manitoMappingRepository.findGroupMapping(groupId, userId);
    }

//    public List<ManitoMapping> getResults(Long groupId) {
//        var manitoMappings = manitoMappingRepository.findAllByGroupId(groupId);
//        manitoMappings.forEach(System.out::println);
//        return manitoMappings;
//    }

}
