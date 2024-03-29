package small.manito.group.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import small.manito.auth.AuthPayload;
import small.manito.group.controller.response.GroupResponse;
import small.manito.group.controller.response.InviteDetailResponse;
import small.manito.querydsl.dto.GroupDTO;
import small.manito.group.service.ManitoGroupQueryService;
import small.manito.global.type.ManitoStatus;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "마니또", description = "마니또 API")
public class ManitoGroupQueryEndpoint {
    private final ManitoGroupQueryService manitoGroupQueryService;

    @GetMapping("/groups")
    List<GroupDTO> getGroups(@RequestParam(name="status") ManitoStatus status,
                             @AuthenticationPrincipal AuthPayload authPayload){
        return manitoGroupQueryService.getManitoGroupWithStatus(authPayload.getUserId(), status);
    }

    @GetMapping("/groups/invited")
    List<GroupDTO> getGroupsInvited(@AuthenticationPrincipal AuthPayload authPayload){
        return manitoGroupQueryService.getManitoGroupInvited(authPayload.getUserId());
    }

    @GetMapping("/groups/{groupId}")
    GroupResponse getGroup(
            @PathVariable(name = "groupId") Long id,
            @AuthenticationPrincipal AuthPayload authPayload){
        var manitoMapping = manitoGroupQueryService.getGroup(id);
        var findGroup = manitoMapping.getManitoGroup();
        return GroupResponse.builder()
                .id(findGroup.getId())
                .name(findGroup.getName())
                .ownerName(manitoMapping.getUser().getUserId())
                .adminId(findGroup.getAdminId())
                .startDate(findGroup.getStartDate())
                .expiredDate(findGroup.getExpiredDate())
                .currentNumber(findGroup.getCurrentNumber())
                .maxNumber(findGroup.getMaxNumber())
                .ownerId(findGroup.getOwnerId())
                .status(findGroup.getStatus())
                .isOwner(authPayload.getUserId().equals(findGroup.getOwnerId()))
                .build();
    }

    @GetMapping("groups/invite-detail")
    List<InviteDetailResponse> getInviteDetail(
            @RequestParam(name = "groupId") Long groupId
    ){
        return manitoGroupQueryService.getInviteDetail(groupId)
                .stream()
                .map(InviteDetailResponse::from)
                .toList();
    }

    @GetMapping("/groups/{groupId}/result")
    void getMembersInManitoGroup(
            @RequestParam(name="adminId") Long adminId,
            @PathVariable(name="groupId") Long groupId
    ){
//        manitoGroupService.getResults(groupId);
    }

}
