package small.manito.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import small.manito.entity.User;
import small.manito.request.UserRequest;
import small.manito.service.ManitoGroupService;

@RestController
@RequiredArgsConstructor
public class UserEndpoint {
    /*
    - 마니또 그룹 아이디가 있고 그룹 아이디를 통해 참여자 등록을 할 수 있다.
    - 참여자 등록 시에는 실제 이름과 닉네임을 필수로 받는다.
     */

    private final ManitoGroupService manitoGroupService;

    /*
    - 마니또 그룹 아이디가 있고 그룹 아이디를 통해 참여자 등록을 할 수 있다.
    - 참여자 등록 시에는 실제 이름과 닉네임을 필수로 받는다.

    GroupId를 어떻게 넘겨야 할까??
     */
    @PostMapping("/join/{groupId}")
    void joinRequestGroup(
            @PathVariable(name="groupId") Long groupId,
            @RequestBody UserRequest userRequest){
        manitoGroupService.join(groupId, userRequest.toUser());
    }

    /* ???
    - 마니또 기간이 지나면 마니또 그룹 상태는 완료 상태가 된다.
    - 완료 상태가 되면 참여자들은 자신의 예상 마니또를 제출하고 실제 마니또가 누구인지 알 수 있다.
     */
    @PostMapping("/predict")
    void predictManito(){

    }

}
