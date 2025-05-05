package wood.mike.sbstravaapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SbStravaApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SbStravaApiApplication.class, args);
    }

}
