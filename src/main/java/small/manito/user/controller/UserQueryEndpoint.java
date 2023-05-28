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
import small.manito.user.service.UserQueryService;
import small.manito.user.service.UserWriteService;

@RestController
@RequiredArgsConstructor
public class UserQueryEndpoint {

    private final UserQueryService userQueryService;

    @GetMapping("users")
    UserResponse getUser(
            @RequestParam("id") Long id
    ){
        return UserResponse.builder()
                .id(userQueryService.getUser(id).getUserId())
                .build();
    }

    @GetMapping("users/my")
    UserResponse getMyInfo(
            @AuthenticationPrincipal AuthPayload authPayload
            ){
        return UserResponse.builder()
                .id(userQueryService.getUser(authPayload.getUserId()).getUserId())
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
