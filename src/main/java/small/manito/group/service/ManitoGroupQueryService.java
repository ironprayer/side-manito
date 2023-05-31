package small.manito.group.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import small.manito.global.type.ManitoStatus;
import small.manito.group.repository.InviteRepository;
import small.manito.group.repository.ManitoGroupRepository;
import small.manito.group.repository.ManitoMappingRepository;
import small.manito.querydsl.dto.GroupDTO;
import small.manito.querydsl.entity.Invite;
import small.manito.querydsl.entity.ManitoGroup;
import small.manito.querydsl.entity.ManitoMapping;
import small.manito.user.repository.UserRepository;

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

    public List<GroupDTO> getManitoGroupInvited(Long userId){
        return inviteRepository
                .findGroupsInGuestId(userId).stream().map((invite -> {
                    return invite.toGroupDTO();
                }))
                .toList();
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
