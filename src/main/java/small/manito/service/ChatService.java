package small.manito.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import small.manito.querydsl.entity.ChatLog;
import small.manito.repository.ChatLogRepository;
import small.manito.repository.ChatRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final ChatLogRepository chatLogRepository;

    public ChatLog saveChatLog(ChatLog chatLog){
        return chatLogRepository.save(chatLog);
    }

    public List<ChatLog> getAllChatInRoom(Long chatId){
        return chatLogRepository.findAllByChatId(chatId);
    }

    List<String> allChatUser(){return null;}
}
