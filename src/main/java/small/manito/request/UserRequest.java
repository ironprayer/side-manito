package small.manito.request;

import lombok.*;
import small.manito.entity.User;

@Builder
@Getter
@ToString
@NoArgsConstructor()
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserRequest {

    // 필수로 입력받을 수 있도록 설정할 수 있는 방법이 있을까?
    // 여기에 groupId가 있을 떄와 없을 때의 장단점이 무엇일까??
    Long userId;
    Long groupId;
    String name;
    String nickName;

    public User toUser(){
        System.out.println("us : " + name + "  ee :" + nickName);
        return User.builder()
                .name(name)
                .nickName(nickName)
                .build();
    }
}
