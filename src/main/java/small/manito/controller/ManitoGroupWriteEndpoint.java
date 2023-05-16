package small.manito.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import small.manito.auth.AuthPayload;
import small.manito.controller.request.ManitoGroupRequest;
import small.manito.service.ManitoGroupService;

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
        manitoGroupService.create(manitoGroupRequest.toManito(), authPayload.getUserId());
    }

    // 마니또 그룹에 지정된 인원이 모두 참여 등록을 하면 마니또 그룹이 준비 상태가 된다. (?)
    // admin 유저가 시작할 수 있다.
    // 시작되면, 각 유저에게 마니또가 매칭이 된다.
    @PostMapping("/groups/start")
    void startManito(
            @RequestBody ManitoGroupRequest manitoGroupRequest
    ){
        manitoGroupService.start(manitoGroupRequest.getAdminId(), manitoGroupRequest.getGroupId());
//        manitoGroupService.matchingManito(manitoGroupRequest.getGroupId());
    }
}
