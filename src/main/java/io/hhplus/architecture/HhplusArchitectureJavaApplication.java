package io.hhplus.architecture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HhplusArchitectureJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(HhplusArchitectureJavaApplication.class, args);
    }

}
