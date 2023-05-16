package small.manito.controller.request;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;
import small.manito.querydsl.entity.ManitoGroup;
import small.manito.type.ManitoStatus;

import java.time.LocalDate;
import java.util.UUID;

@Getter
public class ManitoGroupRequest {
    @Hidden
    private Long groupId;
    @Hidden
    private String adminId;
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
        return  String.valueOf(UUID.randomUUID()).substring(0,5);
    }
}
