package small.manito.controller.request;

import lombok.Getter;

@Getter
public class InviteUserRequest {
    Long groupId;
    String guestId;
}
