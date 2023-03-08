package small.manito.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import small.manito.request.ManitoGroupRequest;
import small.manito.service.ManitoGroupService;

@RestController
public class ManitoGroupEndpoint {
    @Autowired
    ManitoGroupService manitoGroupService;

    /* ( 사용자 정의 자료형 만들 필요가 있겠군 )
        - 마니또 그룹을 만들 때 아래 사항을 지정할 수 있다.
        - 몇명이서 하는지
        - 마니또 기간
        - 마니또 그룹을 만들면 마니또 그룹 아이디랑 어드민 아이디를 발급한다.
     */

    @PostMapping("/group")
    void createManitoGroup(@RequestBody ManitoGroupRequest manitoGroupRequest){
        System.out.println(manitoGroupRequest);
        manitoGroupService.create(manitoGroupRequest.toManito());
      // Group ID, Admin ID 발급
    }

    // 마니또 그룹에 지정된 이원이 모두 참여 등록을 하면 마니또 그룹이 준비 상태가 된다. (?)
    @PatchMapping("/group/start")
    void startManito(
            @RequestBody ManitoGroupRequest manitoGroupRequest
    ){
        var adminId = "000";
        manitoGroupService.start(adminId, manitoGroupRequest.getGroupId());
    }

    // 종료는 어떻게 하지?
    @PatchMapping("/group/end")
    void endManito(
            @RequestBody ManitoGroupRequest manitoGroupRequest
    ) {
        manitoGroupService.end(manitoGroupRequest.getGroupId());
    }

    /* 따로 만들 필요가 있을까? 시작하면서 자동으로 넣어주는게 나을 것 같은데..

    - 마니또 그룹이 준비 상태가 되면 참여자들은 마니또 추첨을 할 수 있다.
    - 마니또 추첨은 랜덤 방식으로 진행되며 자기 자신은 제외되어야 한다.
     */
    @PostMapping("/matching")
    void matchingManito(){
        var groupId = 1L;
        manitoGroupService.matchingManito(groupId);
    }

    /*
    - 자신의 마니또가 누구인지 모두 확인하거나 또는 마니또 그룹 완료 상태라면 마니또 어드민 아이디를 통해 전체 리스트를 확인할 수 있다.
     */
    @GetMapping("/members/groupId")
    void getMembersInManitoGroup(){

    }

    /*
       그룹 목록
     */

}
