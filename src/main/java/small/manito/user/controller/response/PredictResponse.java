package small.manito.user.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import small.manito.global.type.ManitoResultStatus;
import small.manito.querydsl.entity.ManitoMapping;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class PredictResponse {
    Long userId;
    Long maniteeId;
    String maniteeName;
    ManitoResultStatus manitoResultStatus;

    public static PredictResponse from(ManitoMapping manitoMapping){
        return PredictResponse.builder()
                .userId(manitoMapping.getManito().getId())
                .maniteeId(manitoMapping.getUser().getId())
                .maniteeName(manitoMapping.getResult() == ManitoResultStatus.NOTSUBMIT
                        ? null
                        : manitoMapping.getUser().getUserId())
                .manitoResultStatus(manitoMapping.getResult())
                .build();
    }
}
