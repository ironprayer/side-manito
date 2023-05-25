package small.manito.group.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import small.manito.global.type.ManitoStatus;

import java.time.LocalDate;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class GroupResponse {
    Long id;
    String ownerName;
    String adminId;
    String name;
    Long currentNumber;
    Long maxNumber;
    Long ownerId;
    LocalDate startDate;
    LocalDate expiredDate;
    ManitoStatus status;
    Boolean isOwner;
}
