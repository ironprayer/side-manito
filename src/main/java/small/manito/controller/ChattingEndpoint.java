package small.manito.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import small.manito.controller.request.ChatRequest;
import small.manito.controller.response.ChatResponse;
import small.manito.global.exception.InvalidUserException;
import small.manito.service.ChatService;

@Controller
@RequiredArgsConstructor
public class ChattingEndpoint {
    private final ChatService chatService;

    @MessageMapping("/chat/{chatId}")
    @SendTo("/topic/chat/{chatId}")
    public ChatResponse chatting(ChatRequest chatRequest,
                             @DestinationVariable String chatId) throws Exception {

        chatService.saveChatLog(chatRequest.toChatLog(Long.valueOf(chatId)));

        return ChatResponse.builder()
                .sendUserId(chatRequest.getSendUserId())
                .message(chatRequest.getMessage())
                .createdAt(chatRequest.getCreatedAt())
                .build();
    }
}
