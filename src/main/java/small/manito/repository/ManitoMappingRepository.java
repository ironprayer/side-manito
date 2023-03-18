package small.manito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import small.manito.querydsl.entity.ManitoMapping;
import small.manito.repository.custom.ManitoGroupMappingCustomRepository;

import java.util.List;

@Repository
public interface ManitoMappingRepository extends JpaRepository<ManitoMapping, Long>, ManitoGroupMappingCustomRepository {
    List<ManitoMapping> findAllByGroupId(Long groupId);
}
