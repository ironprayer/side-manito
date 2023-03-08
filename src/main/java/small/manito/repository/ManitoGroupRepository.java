package small.manito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import small.manito.entity.ManitoGroup;

@Repository
public interface ManitoGroupRepository extends JpaRepository<ManitoGroup, Long> {
}
