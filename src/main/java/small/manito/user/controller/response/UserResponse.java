package small.manito.user.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@Builder
@ToString
public class UserResponse {
    private Long id;
    private String userId;
    private String name;
    private String nickName;
}
