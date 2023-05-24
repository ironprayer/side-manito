package small.manito.controller.request;

import lombok.Getter;
import lombok.ToString;
import small.manito.querydsl.entity.ChatLog;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@ToString
public class ChatRequest {
    Long sendUserId;
    String message;
    LocalDateTime createdAt;

    public ChatLog toChatLog(Long chatId){
        return ChatLog.builder()
                .chatId(chatId)
                .sendId(this.sendUserId)
                .message(this.message)
                .createdAt(this.createdAt)
                .build();
    }
}
