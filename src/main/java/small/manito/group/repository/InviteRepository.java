package small.manito.group.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import small.manito.querydsl.entity.Invite;
import small.manito.querydsl.entity.ManitoGroup;
import small.manito.querydsl.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface InviteRepository extends JpaRepository<Invite, Long> {
    @Query("select i from Invite i where i.guest.id = :guestId and i.status = PENDING ")
    List<Invite> findGroupsInGuestId(Long guestId);
    List<Invite> findAllByManitoGroup(ManitoGroup manitoGroup);
    Optional<Invite> findByGuest(User gest);
    Optional<Invite> findByManitoGroupAndGuest(ManitoGroup manitoGroup, User guest);
}
