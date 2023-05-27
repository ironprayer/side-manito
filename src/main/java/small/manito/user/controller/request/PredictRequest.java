package small.manito.user.controller.request;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PredictRequest {
    Long groupId;
    String maniteeName;
}
