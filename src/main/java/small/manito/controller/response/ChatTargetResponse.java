package small.manito.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import small.manito.querydsl.entity.ManitoMapping;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ChatTargetResponse {
    Long manitoChatId;
    Long maniteeChatId;


    public static ChatTargetResponse from(List<ManitoMapping> manitoMappings, Long userId){
        var manitoChatId = -1L;
        var maniteeChatId = -1L;
        for(var manitoMapping : manitoMappings){
            if(manitoMapping.getUser().getId().equals(userId)){
                manitoChatId = manitoMapping.getChat().getId();
            } else {
                maniteeChatId = manitoMapping.getChat().getId();
            }
        }

        return ChatTargetResponse.builder()
                .manitoChatId(manitoChatId)
                .maniteeChatId(maniteeChatId)
                .build();
    }
}
