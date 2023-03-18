package small.manito.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import small.manito.request.UserRequest;
import small.manito.service.ManitoGroupService;

@RestController
@RequiredArgsConstructor
public class UserWriteEndpoint {
    private final ManitoGroupService manitoGroupService;

    /*
    - 마니또 그룹 아이디가 있고 그룹 아이디를 통해 참여자 등록을 할 수 있다.
    - 참여자 등록 시에는 실제 이름과 닉네임을 필수로 받는다.

     */
    @PostMapping("users/join")
    void joinRequestGroup(
            @RequestBody UserRequest userRequest){
        manitoGroupService.join(userRequest.getGroupId(), userRequest.toUser());
    }

}
