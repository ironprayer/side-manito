package small.manito.group.controller.request;

import lombok.Getter;

@Getter
public class InviteUserRequest {
    Long groupId;
    String guestId;
}
