package small.manito.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import small.manito.querydsl.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);
}
