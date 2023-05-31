package small.manito.querydsl.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;
import small.manito.global.type.ManitoResultStatus;

@ToString
@Getter
public class GroupMappingDTO {
    private Long userId;
    private Long maniteeId;
    private String maniteeName;
    private ManitoResultStatus manitoResultStatus;

    @QueryProjection
    public GroupMappingDTO(Long userId, Long maniteeId, String manitoName, ManitoResultStatus manitoResultStatus) {
        this.userId = userId;
        this.maniteeId = maniteeId;
        this.maniteeName = maniteeName;
        this.manitoResultStatus = manitoResultStatus;
    }

    public void changeStatus(ManitoResultStatus status) {
        this.manitoResultStatus = status;
    }
}
