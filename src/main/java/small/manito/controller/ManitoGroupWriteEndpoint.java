package small.manito.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import small.manito.auth.AuthPayload;
import small.manito.controller.request.InviteAnswerRequest;
import small.manito.controller.request.InviteUserRequest;
import small.manito.controller.request.ManitoGroupRequest;
import small.manito.controller.request.StartGroupRequest;
import small.manito.service.ManitoGroupService;
import small.manito.global.type.InviteAnswer;

@RestController
@RequiredArgsConstructor
public class ManitoGroupWriteEndpoint {
    private final ManitoGroupService manitoGroupService;

    /* ( 사용자 정의 자료형 만들 필요가 있겠군 )
        - 마니또 그룹을 만들 때 아래 사항을 지정할 수 있다.
        - 몇명이서 하는지
        - 마니또 기간
        - 마니또 그룹을 만들면 마니또 그룹 아이디랑 어드민 아이디를 발급한다.
     */
    @PostMapping("/groups")
    void createManitoGroup(@RequestBody ManitoGroupRequest manitoGroupRequest,
                           @AuthenticationPrincipal AuthPayload authPayload){
        var userId = authPayload.getUserId();
        manitoGroupService.create(manitoGroupRequest.toManito(userId), userId);
    }

    @PostMapping("/groups/invite")
    void inviteManitoGroup(@RequestBody InviteUserRequest inviteUserRequest,
                           @AuthenticationPrincipal AuthPayload authPayload){
        /* TODO 수정 필요
           1. 자신한테 초대 안되게하기
           2. PENDING 유저에게는 초대 못 보내게 하기.
         */
        manitoGroupService.inviteUser(inviteUserRequest.getGroupId(), authPayload.getUserId(), inviteUserRequest.getGuestId());
    }

    /*
 - 마니또 그룹 아이디가 있고 그룹 아이디를 통해 참여자 등록을 할 수 있다.
 - 참여자 등록 시에는 실제 이름과 닉네임을 필수로 받는다.

  */
    //@Operation(security = { @SecurityRequirement(name = "bearer-key") })
    @PostMapping("/groups/invite/answer")
    void answerGroup(
            @RequestBody InviteAnswerRequest inviteAnswerRequest,
            @AuthenticationPrincipal AuthPayload authPayload){

        manitoGroupService.answerInvited(InviteAnswer
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
        manitoGroupService.start(startGroupRequest.getGroupId(), authPayload.getUserId());
//        manitoGroupService.matchingManito(manitoGroupRequest.getGroupId());
    }
}
