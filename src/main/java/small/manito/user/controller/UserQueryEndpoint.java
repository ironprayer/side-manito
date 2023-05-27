package small.manito.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import small.manito.auth.AuthPayload;
import small.manito.user.controller.response.ChatTargetResponse;
import small.manito.user.controller.response.PredictResponse;
import small.manito.user.controller.response.UserResponse;
import small.manito.querydsl.dto.GroupMappingDTO;
import small.manito.group.service.ManitoGroupQueryService;

@RestController
@RequiredArgsConstructor
public class UserQueryEndpoint {

    private final ManitoGroupQueryService manitoGroupQueryService;
    // 궁금증 컨트롤러가 다른데 도메인 시작이 같아도 될까??

    @GetMapping("users")
    UserResponse getUser(
            @RequestParam("id") Long id
    ){
        return UserResponse.builder()
                .id(manitoGroupQueryService.getUser(id).getUserId())
                .build();
    }

    @GetMapping("users/my")
    UserResponse getMyInfo(
            @AuthenticationPrincipal AuthPayload authPayload
            ){
        return UserResponse.builder()
                .id(manitoGroupQueryService.getUser(authPayload.getUserId()).getUserId())
                .build();
    }

    /* ???
    - 마니또 기간이 지나면 마니또 그룹 상태는 완료 상태가 된다.
    - 완료 상태가 되면 참여자들은 자신의 예상 마니또를 제출하고 실제 마니또가 누구인지 알 수 있다.
    */
    @GetMapping("users/predict")
    PredictResponse predictManito(
            @RequestParam(name = "groupId") Long groupId,
            @AuthenticationPrincipal AuthPayload authPayload
    ){
        return PredictResponse.from(manitoGroupQueryService.getManitoResult(groupId, authPayload.getUserId()));
    }

    @GetMapping("users/chat-targets")
    ChatTargetResponse getChatTargets(
            @RequestParam(name = "groupId") Long groupId,
            @AuthenticationPrincipal AuthPayload authPayload
    ){
        var userId = authPayload.getUserId();

        return ChatTargetResponse
                .from(manitoGroupQueryService.getChatTargets(groupId, userId),
                        userId);
    }
}
