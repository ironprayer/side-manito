package small.manito.user.controller.request;

import lombok.*;
import small.manito.querydsl.entity.User;

@Getter
public class UserRequest {
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
