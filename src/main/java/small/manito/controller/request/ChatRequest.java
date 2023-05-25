package small.manito.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import small.manito.querydsl.entity.ChatLog;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@ToString
public class ChatRequest {
    Long sendUserId;
    String message;

    public ChatLog toChatLog(Long chatId){
        return ChatLog.builder()
                .chatId(chatId)
                .sendId(this.sendUserId)
                .message(this.message)
                .build();
    }
}
