package small.manito.group.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import small.manito.auth.AuthPayload;
import small.manito.group.controller.request.InviteAnswerRequest;
import small.manito.group.controller.request.InviteUserRequest;
import small.manito.group.controller.request.ManitoGroupRequest;
import small.manito.group.controller.request.StartGroupRequest;
import small.manito.group.service.ManitoGroupQueryService;
import small.manito.global.type.InviteAnswer;
import small.manito.group.service.ManitoGroupWriteService;

@RestController
@RequiredArgsConstructor
public class ManitoGroupWriteEndpoint {
    private final ManitoGroupWriteService manitoGroupWriteService;

    @PostMapping("/groups")
    void createManitoGroup(@RequestBody ManitoGroupRequest manitoGroupRequest,
                           @AuthenticationPrincipal AuthPayload authPayload){
        var userId = authPayload.getUserId();
        manitoGroupWriteService.create(manitoGroupRequest.toManito(userId), userId);
    }

    @PostMapping("/groups/invite")
    void inviteManitoGroup(@RequestBody InviteUserRequest inviteUserRequest,
                           @AuthenticationPrincipal AuthPayload authPayload){
        manitoGroupWriteService.inviteUser(inviteUserRequest.getGroupId(), authPayload.getUserId(), inviteUserRequest.getGuestId());
    }

    //@Operation(security = { @SecurityRequirement(name = "bearer-key") })
    @PostMapping("/groups/invite/answer")
    void answerGroup(
            @RequestBody InviteAnswerRequest inviteAnswerRequest,
            @AuthenticationPrincipal AuthPayload authPayload){

        manitoGroupWriteService.answerInvited(InviteAnswer
                .builder()
                .groupId(inviteAnswerRequest.getGroupId())
                .userId(authPayload.getUserId())
                .isAccept(inviteAnswerRequest.getIsAccept())
                .build());
    }


    // 마니또 그룹에 지정된 인원이 모두 참여 등록을 하면 마니또 그룹이 준비 상태가 된다. (?)
    // admin 유저가 시작할 수 있다.
    // 시작되면, 각 유저에게 마니또가 매칭이 된다.
    @PutMapping("/groups/start")
    void startManito(
            @RequestBody StartGroupRequest startGroupRequest,
            @AuthenticationPrincipal AuthPayload authPayload
    ){
        manitoGroupWriteService.start(startGroupRequest.getGroupId(), authPayload.getUserId());
//        manitoGroupService.matchingManito(manitoGroupRequest.getGroupId());
    }
}
