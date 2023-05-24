package small.manito.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import small.manito.controller.response.ChatResponse;
import small.manito.querydsl.entity.ChatLog;
import small.manito.service.ChatService;

import java.util.Comparator;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatQueryEndpoint {
    private final ChatService chatService;

    @GetMapping("chat")
    List<ChatResponse> getAllChatInRoom(
            @RequestParam("chatId") Long chatId
    ){
        return chatService.getAllChatInRoom(chatId)
                .stream()
                .sorted(Comparator.comparing(ChatLog::getCreatedAt))
                .map(ChatResponse::from)
                .toList();
    }
}
