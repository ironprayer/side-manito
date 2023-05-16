package small.manito.querydsl.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;
import small.manito.type.ManitoStatus;

import java.time.LocalDate;

@ToString
@Getter
public class GroupDTO {
    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate expiredDate;
    private ManitoStatus status;

    @QueryProjection
    public GroupDTO(Long id, String name, LocalDate startDate, LocalDate expiredDate, ManitoStatus status) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.expiredDate = expiredDate;
        this.status = status;
    }
}
