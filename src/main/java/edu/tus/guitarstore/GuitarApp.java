package edu.tus.guitarstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class GuitarApp {

    public static void main(String[] args) {
        SpringApplication.run(GuitarApp.class, args);
    }
}
