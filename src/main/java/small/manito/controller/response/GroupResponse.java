package small.manito.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class GroupResponse {
    Long id;
    String adminId;
    String name;
    Long currentNumber;
    Long maxNumber;
    Long ownerId;
    LocalDate startDate;
    LocalDate expireDate;
}
