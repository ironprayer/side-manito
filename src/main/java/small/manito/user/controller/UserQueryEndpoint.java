package small.manito.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import small.manito.auth.AuthPayload;
import small.manito.querydsl.entity.User;
import small.manito.user.controller.response.ChatTargetResponse;
import small.manito.user.controller.response.PredictResponse;
import small.manito.user.controller.response.UserResponse;
import small.manito.user.service.UserQueryService;

@RestController
@RequiredArgsConstructor
public class UserQueryEndpoint {

    private final UserQueryService userQueryService;

    @GetMapping("users")
    UserResponse getUser(
            @RequestParam("id") Long id
    ){
        User user = userQueryService.getUser(id);
        return UserResponse.builder()
                .id(user.getId())
                .userId(user.getUserId())
                .nickName(user.getNickName())
                .name(user.getName())
                .build();
    }

    @GetMapping("users/my")
    UserResponse getMyInfo(
            @AuthenticationPrincipal AuthPayload authPayload
            ){
        User user = userQueryService.getUser(authPayload.getUserId());
        return UserResponse.builder()
                .id(user.getId())
                .userId(user.getUserId())
                .nickName(user.getNickName())
                .name(user.getName())
                .build();
    }

    @GetMapping("users/predict")
    PredictResponse predictManito(
            @RequestParam(name = "groupId") Long groupId,
            @AuthenticationPrincipal AuthPayload authPayload
    ){
        return PredictResponse.from(userQueryService.getManitoResult(groupId, authPayload.getUserId()));
    }

    @GetMapping("users/chat-targets")
    ChatTargetResponse getChatTargets(
            @RequestParam(name = "groupId") Long groupId,
            @AuthenticationPrincipal AuthPayload authPayload
    ){
        var userId = authPayload.getUserId();

        return ChatTargetResponse
                .from(userQueryService.getChatTargets(groupId, userId),
                        userId);
    }
}
