package small.manito.request;

import lombok.*;
import small.manito.entity.ManitoGroup;
import small.manito.type.ManitoStatus;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@Getter
@ToString
@NoArgsConstructor()
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ManitoGroupRequest {
    private Long groupId;
    private String name;
    private Long maxNumber;
    private LocalDate startDate;
    private LocalDate expiredDate;

    public ManitoGroup toManito(){
        return ManitoGroup.builder()
                .name(name)
                .maxNumber(maxNumber)
                .adminId(generateAdminId())
                .currentNumber(0L)
                .status(ManitoStatus.WAITING)
                .startDate(startDate)
                .expiredDate(expiredDate)
                .build();
    }

    private String generateAdminId(){
        return String.valueOf(UUID.randomUUID());
    }
}
