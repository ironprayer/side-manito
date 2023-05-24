package small.manito.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import small.manito.global.type.InviteAnswerStatus;
import small.manito.querydsl.entity.Invite;

@Getter
@Builder
@AllArgsConstructor
public class InviteDetailResponse {
    String name;
    InviteAnswerStatus status;

    public static InviteDetailResponse from(Invite invite){
        return InviteDetailResponse.builder()
                .name(invite.getGuest().getUserId())
                .status(invite.getStatus())
                .build();
    }
}
