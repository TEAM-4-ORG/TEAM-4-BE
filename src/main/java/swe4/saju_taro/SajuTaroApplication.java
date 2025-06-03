package swe4.saju_taro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SajuTaroApplication {

	public static void main(String[] args) {
		SpringApplication.run(SajuTaroApplication.class, args);
	}

}
