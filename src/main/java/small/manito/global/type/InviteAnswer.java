package small.manito.global.type;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class InviteAnswer {
    Long groupId;
    Long userId;
    Boolean isAccept;
}
