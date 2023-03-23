package small.manito.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthEndpoint {
    @GetMapping("/hc")
    String hc(){
        return "ok";
    }
}
