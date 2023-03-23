package small.manito.controller.request;

import lombok.*;
import small.manito.querydsl.entity.User;

@Getter
public class UserRequest {

    // 필수로 입력받을 수 있도록 설정할 수 있는 방법이 있을까?
    Long userId;
    Long groupId;
    String name;
    String nickName;
    Long predictManitoId;

    public User toUser(){
        return User.builder()
                .name(name)
                .nickName(nickName)
                .build();
    }
}
