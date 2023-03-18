package small.manito.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import small.manito.querydsl.entity.ManitoMapping;
import small.manito.request.ManitoGroupRequest;
import small.manito.service.ManitoGroupService;

import java.util.List;
import java.util.Random;

@RestController
@RequiredArgsConstructor
public class ManitoGroupEndpoint {
    private final ManitoGroupService manitoGroupService;

    /* ( 사용자 정의 자료형 만들 필요가 있겠군 )
        - 마니또 그룹을 만들 때 아래 사항을 지정할 수 있다.
        - 몇명이서 하는지
        - 마니또 기간
        - 마니또 그룹을 만들면 마니또 그룹 아이디랑 어드민 아이디를 발급한다.
     */
    @PostMapping("/groups")
    void createManitoGroup(@RequestBody ManitoGroupRequest manitoGroupRequest){
        manitoGroupService.create(manitoGroupRequest.toManito());
    }

    // 마니또 그룹에 지정된 인원이 모두 참여 등록을 하면 마니또 그룹이 준비 상태가 된다. (?)
    // admin 유저가 시작할 수 있다.
    // 시작되면, 각 유저에게 마니또가 매칭이 된다.
    @PostMapping("/groups/start")
    void startManito(
            @RequestBody ManitoGroupRequest manitoGroupRequest
    ){
        manitoGroupService.start(manitoGroupRequest.getAdminId(), manitoGroupRequest.getGroupId());
        manitoGroupService.matchingManito(manitoGroupRequest.getGroupId());
    }

    /*
    - 자신의 마니또가 누구인지 모두 확인하거나 또는 마니또 그룹 완료 상태라면 마니또 어드민 아이디를 통해 전체 리스트를 확인할 수 있다.
     */
    @GetMapping("/groups/result")
    void getMembersInManitoGroup(
            @RequestParam(name="adminId") Long adminId,
            @RequestParam(name="groupId") Long groupId
    ){
        manitoGroupService.getResults(groupId);
    }

}
