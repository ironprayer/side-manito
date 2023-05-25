package small.manito.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import small.manito.querydsl.entity.ChatLog;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@ToString
public class ChatResponse {
    Long id;
    Long sendUserId;
    String message;
    LocalDateTime createdAt;

    public static ChatResponse from(ChatLog chatLog){
        return ChatResponse.builder()
                .id(chatLog.getId())
                .sendUserId(chatLog.getSendId())
                .message(chatLog.getMessage())
                .createdAt(chatLog.getCreatedAt())
                .build();
    }
}
