package small.manito;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ManitoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManitoApplication.class, args);
	}

}
