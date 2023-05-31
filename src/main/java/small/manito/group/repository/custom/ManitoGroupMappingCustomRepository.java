package small.manito.group.repository.custom;

import small.manito.global.type.ManitoStatus;
import small.manito.querydsl.dto.GroupDTO;

import java.util.List;

public interface ManitoGroupMappingCustomRepository {
    List<GroupDTO> findGroupsWithUserIdAndStatus(Long userId, ManitoStatus status);
}
