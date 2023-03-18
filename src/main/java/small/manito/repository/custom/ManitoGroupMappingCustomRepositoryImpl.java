package small.manito.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import small.manito.querydsl.dto.GroupMappingDTO;
import small.manito.querydsl.dto.QGroupMappingDTO;

import static small.manito.querydsl.entity.QManitoMapping.manitoMapping;

public class ManitoGroupMappingCustomRepositoryImpl implements ManitoGroupMappingCustomRepository {
    private final JPAQueryFactory queryFactory;

    public ManitoGroupMappingCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public GroupMappingDTO findGroupMapping(Long groupId, Long userId) {
        return queryFactory
                .select(new QGroupMappingDTO(
                        manitoMapping.user.id,
                        manitoMapping.manito.id,
                        manitoMapping.manito.name,
                        manitoMapping.result)
                )
                .from(manitoMapping)
                .where(manitoMapping.groupId.eq(groupId))
                .where(manitoMapping.user.id.eq(userId))
                .fetchOne();
    }
}
