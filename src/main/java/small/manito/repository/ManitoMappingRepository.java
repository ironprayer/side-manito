package small.manito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import small.manito.querydsl.entity.ManitoGroup;
import small.manito.repository.custom.ManitoGroupMappingCustomRepository;
import small.manito.querydsl.entity.ManitoMapping;

import java.util.List;

@Repository
public interface ManitoMappingRepository extends JpaRepository<ManitoMapping, Long>, ManitoGroupMappingCustomRepository {

    List<ManitoMapping> findAllByManitoGroup(ManitoGroup manitoGroup);
}
