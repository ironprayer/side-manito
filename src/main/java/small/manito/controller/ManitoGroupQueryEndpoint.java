package small.manito.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import small.manito.auth.AuthPayload;
import small.manito.controller.response.GroupResponse;
import small.manito.querydsl.dto.GroupDTO;
import small.manito.service.ManitoGroupService;
import small.manito.global.type.ManitoStatus;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "마니또", description = "마니또 API")
public class ManitoGroupQueryEndpoint {
    private final ManitoGroupService manitoGroupService;

    @GetMapping("/groups")
    List<GroupDTO> getGroups(@RequestParam(name="status") ManitoStatus status,
                             @AuthenticationPrincipal AuthPayload authPayload){
        return manitoGroupService.getManitoGroupWithStatus(authPayload.getUserId(), status);
    }

    @GetMapping("/groups/invited")
    List<GroupDTO> getGroupsInvited(@AuthenticationPrincipal AuthPayload authPayload){
        return manitoGroupService.getManitoGroupInvited(authPayload.getUserId());
    }

    @GetMapping("/groups/{groupId}")
    GroupResponse getGroup(@PathVariable(name = "groupId") Long id){
        var findGroup = manitoGroupService.getGroup(id);
        return GroupResponse.builder()
                .id(findGroup.getId())
                .name(findGroup.getName())
                .adminId(findGroup.getAdminId())
                .startDate(findGroup.getStartDate())
                .expireDate(findGroup.getExpiredDate())
                .currentNumber(findGroup.getCurrentNumber())
                .maxNumber(findGroup.getMaxNumber())
                .ownerId(findGroup.getOwnerId())
                .build();
    }


    /*
    - 자신의 마니또가 누구인지 모두 확인하거나 또는 마니또 그룹 완료 상태라면 마니또 어드민 아이디를 통해 전체 리스트를 확인할 수 있다.
    식별할 수 있는 값의 경우 url resource에 포함시켜주는 것이 좋다.
     */
    @GetMapping("/groups/{groupId}/result")
    void getMembersInManitoGroup(
            @RequestParam(name="adminId") Long adminId,
            @PathVariable(name="groupId") Long groupId
    ){
//        manitoGroupService.getResults(groupId);
    }

}
