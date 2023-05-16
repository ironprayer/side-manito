package small.manito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import small.manito.querydsl.entity.ManitoGroup;

@Repository
public interface ManitoGroupRepository extends JpaRepository<ManitoGroup, Long> {
    @Modifying(clearAutomatically = true)
    @Query("update ManitoGroup m set m.status = small.manito.type.ManitoStatus.ENDED where m.expiredDate <= current_timestamp")
    void endOfAllMantioGroup();
}
