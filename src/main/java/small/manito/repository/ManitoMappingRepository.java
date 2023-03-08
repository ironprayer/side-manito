package small.manito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import small.manito.entity.ManitoMapping;

import java.util.List;

@Repository
public interface ManitoMappingRepository extends JpaRepository<ManitoMapping, Long> {
    List<ManitoMapping> findAllByGroupId(Long groupId);
}
