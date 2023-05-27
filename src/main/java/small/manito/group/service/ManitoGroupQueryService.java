package small.manito.group.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import small.manito.global.exception.InvalidUserException;
import small.manito.global.exception.UnAuthorizedException;
import small.manito.global.exception.UserNumberFallShortException;
import small.manito.global.exception.UserNumberOverException;
import small.manito.global.type.ManitoResultStatus;
import small.manito.querydsl.dto.GroupDTO;
import small.manito.querydsl.dto.GroupMappingDTO;
import small.manito.querydsl.entity.*;
import small.manito.group.repository.InviteRepository;
import small.manito.group.repository.ManitoGroupRepository;
import small.manito.group.repository.ManitoMappingRepository;
import small.manito.user.repository.UserRepository;
import small.manito.global.type.InviteAnswer;
import small.manito.global.type.InviteAnswerStatus;
import small.manito.global.type.ManitoStatus;

import java.util.List;

@Getter
@Service
@RequiredArgsConstructor
public class ManitoGroupQueryService {

    private final ManitoGroupRepository manitoGroupRepository;
    private final ManitoMappingRepository manitoMappingRepository;
    private final InviteRepository inviteRepository;
    private final UserRepository userRepository;

    @Transactional
    public ManitoMapping getGroup(Long groupId){
        return manitoMappingRepository.findAllByManitoGroup(
                ManitoGroup.builder()
                        .id(groupId)
                        .build()
        ).stream()
                .filter(manitoMapping ->
                {return manitoMapping.getManitoGroup().getOwnerId().equals(
                        manitoMapping.getUser().getId()
                );})
                .findFirst().get();
    }

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

    // 별도로 빠져있는 것이 좋다 -> package 하나 만들자
    @Scheduled(fixedDelay = 1000 * 60)
    @Transactional
    public void end(){
        manitoGroupRepository.endOfAllMantioGroup();
        // flush가 자동으로 되었던가? 아니면 flush를 시켜줘야 했던가? 해야한다면 방법은?
    }

    // manito 맞췄는지 여부를 Back-End쪽에서 가지고 있을 필요가 있을까? ( 어드민 유저가 보고싶다면 ? )
    // 여기에 queryDSL 사용해보면 되겠는데
    @Transactional
    public ManitoMapping getManitoResult(Long groupId, Long userId) {
        return manitoMappingRepository.findResultManitoMapping(groupId, userId).get();
    }

    @Transactional
    public List<ManitoMapping> getChatTargets(Long groupId, Long userId){
        return manitoMappingRepository.findChatTargets(groupId, userId);
    }

    @Transactional
    public List<Invite> getInviteDetail(Long groupId){
        return inviteRepository.findAllByManitoGroup(
                ManitoGroup.builder()
                        .id(groupId)
                        .build()
        );
    }

}
