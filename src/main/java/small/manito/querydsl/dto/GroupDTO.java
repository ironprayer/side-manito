package small.manito.querydsl.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import small.manito.type.ManitoStatus;

import java.time.LocalDate;

@ToString
@Getter
@Builder
public class GroupDTO {
    private Long id;
    private String name;
    private Boolean isOwner;
    private LocalDate startDate;
    private LocalDate expiredDate;
    private ManitoStatus status;

    @QueryProjection
    public GroupDTO(Long id, String name, Boolean isOwner, LocalDate startDate, LocalDate expiredDate, ManitoStatus status) {
        this.id = id;
        this.name = name;
        this.isOwner = isOwner;
        this.startDate = startDate;
        this.expiredDate = expiredDate;
        this.status = status;
    }
}
