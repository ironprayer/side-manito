package small.manito.group.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import small.manito.querydsl.entity.ManitoGroup;
import small.manito.group.repository.custom.ManitoGroupMappingCustomRepository;
import small.manito.querydsl.entity.ManitoMapping;

import java.util.List;
import java.util.Optional;

@Repository
public interface ManitoMappingRepository extends JpaRepository<ManitoMapping, Long>, ManitoGroupMappingCustomRepository {

    List<ManitoMapping> findAllByManitoGroup(ManitoGroup manitoGroup);

    @Query("select m from ManitoMapping m where m.manitoGroup.id = :groupId and (m.user.id = :userId or m.manito.id = :userId)")
    List<ManitoMapping> findChatTargets(@Param("groupId") Long groupId, @Param("userId") Long userId);

    @Query("select m from ManitoMapping m where m.manitoGroup.id = :groupId and m.manito.id = :userId")
    Optional<ManitoMapping> findResultManitoMapping(@Param("groupId") Long groupId, @Param("userId") Long userId);
}
