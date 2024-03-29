package small.manito.group.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import small.manito.global.type.ManitoStatus;
import small.manito.querydsl.dto.GroupDTO;
import small.manito.querydsl.dto.QGroupDTO;

import java.util.List;

import static small.manito.querydsl.entity.QManitoMapping.manitoMapping;

public class ManitoGroupMappingCustomRepositoryImpl implements ManitoGroupMappingCustomRepository {
    private final JPAQueryFactory queryFactory;

    public ManitoGroupMappingCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }
    @Override
    public List<GroupDTO> findGroupsWithUserIdAndStatus(Long userId, ManitoStatus status) {
        return queryFactory.from(manitoMapping)
                .select(new QGroupDTO(
                        manitoMapping.manitoGroup.id,
                        manitoMapping.manitoGroup.name,
                        manitoMapping.manitoGroup.ownerId.eq(userId),
                        manitoMapping.manitoGroup.startDate,
                        manitoMapping.manitoGroup.expiredDate,
                        manitoMapping.manitoGroup.status
                ))
                .where(manitoMapping.manitoGroup.status.eq(status))
                .where(manitoMapping.user.id.eq(userId))
                .fetch();
    }
}
