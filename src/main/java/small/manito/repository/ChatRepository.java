package small.manito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import small.manito.querydsl.entity.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
