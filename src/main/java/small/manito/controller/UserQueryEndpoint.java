package small.manito.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import small.manito.querydsl.dto.GroupMappingDTO;
import small.manito.service.ManitoGroupService;

@RestController
@RequiredArgsConstructor
public class UserQueryEndpoint {

    private final ManitoGroupService manitoGroupService;
    // 궁금증 컨트롤러가 다른데 도메인 시작이 같아도 될까??

    /* ???
    - 마니또 기간이 지나면 마니또 그룹 상태는 완료 상태가 된다.
    - 완료 상태가 되면 참여자들은 자신의 예상 마니또를 제출하고 실제 마니또가 누구인지 알 수 있다.
    */
    @GetMapping("users/predict")
    GroupMappingDTO predictManito(
            @RequestParam(name = "groupId") Long groupId,
            @RequestParam(name = "userId") Long userId,
            @RequestParam(name = "manitoName") String manitoName
    ){
        return manitoGroupService.getManitoResult(groupId, userId, manitoName);
    }

    // 채팅 기록
    @GetMapping("users/chat")
    void getChat(){}
}
