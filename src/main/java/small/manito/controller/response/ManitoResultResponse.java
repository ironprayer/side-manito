package small.manito.controller.response;

import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public class ManitoResultResponse {
    private List<Map<String, String>> manitoResult;
}