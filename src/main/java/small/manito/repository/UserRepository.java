package small.manito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import small.manito.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
