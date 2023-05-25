package small.manito.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import small.manito.querydsl.entity.ChatLog;

import java.util.List;

public interface ChatLogRepository extends JpaRepository<ChatLog, Long> {
    List<ChatLog> findAllByChatId(Long chatId);
}
