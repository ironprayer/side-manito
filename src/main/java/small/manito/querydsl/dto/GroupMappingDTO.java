package small.manito.querydsl.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.ToString;
import small.manito.global.type.ManitoResultStatus;

@ToString
public class GroupMappingDTO {
    private Long userId;
    private Long manitoId;
    private String manitoName;
    private ManitoResultStatus manitoResultStatus;

    @QueryProjection
    public GroupMappingDTO(Long userId, Long manitoId, String manitoName, ManitoResultStatus manitoResultStatus) {
        this.userId = userId;
        this.manitoId = manitoId;
        this.manitoName = manitoName;
        this.manitoResultStatus = manitoResultStatus;
    }
}
