package small.manito.repository.custom;

import org.springframework.data.jpa.repository.JpaRepository;
import small.manito.querydsl.dto.GroupMappingDTO;
import small.manito.querydsl.entity.ManitoMapping;

public interface ManitoGroupMappingCustomRepository {
    GroupMappingDTO findGroupMapping(Long groupId, Long userId);
}
