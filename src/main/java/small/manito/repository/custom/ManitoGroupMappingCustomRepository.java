package small.manito.repository.custom;

import small.manito.querydsl.dto.GroupDTO;
import small.manito.querydsl.dto.GroupMappingDTO;
import small.manito.global.type.ManitoStatus;

import java.util.List;

public interface ManitoGroupMappingCustomRepository {
    GroupMappingDTO findGroupMapping(Long groupId, Long userId);
    List<GroupDTO> findGroupsWithUserIdAndStatus(Long userId, ManitoStatus status);
}
