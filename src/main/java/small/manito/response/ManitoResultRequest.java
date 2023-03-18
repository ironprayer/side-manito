package small.manito.response;

import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public class ManitoResultRequest {
    private List<Map<String, String>> manitoResult;
}
