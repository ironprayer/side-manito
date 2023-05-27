package small.manito.user.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import small.manito.querydsl.entity.ManitoMapping;

import java.util.List;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class ChatTargetResponse {
    Long manitoChatId; // 내가 도와주는 사람
    Long maniteeChatId; // 나를 도와주는 사람
    String manitoName;


    public static ChatTargetResponse from(List<ManitoMapping> manitoMappings, Long userId){
        var manitoChatId = -1L;
        var maniteeChatId = -1L;
        var manitoName = "";
        for(var manitoMapping : manitoMappings){
            if(manitoMapping.getUser().getId().equals(userId)){
                manitoChatId = manitoMapping.getChat().getId();
                manitoName = manitoMapping.getManito().getUserId();
            } else {
                maniteeChatId = manitoMapping.getChat().getId();
            }
        }

        return ChatTargetResponse.builder()
                .manitoChatId(manitoChatId)
                .maniteeChatId(maniteeChatId)
                .manitoName(manitoName)
                .build();
    }
}
