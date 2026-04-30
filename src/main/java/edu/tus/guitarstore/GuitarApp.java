package edu.tus.guitarstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class GuitarApp {

    /**
     * main method to run the application.
     * @param args
     */
    public static void main(final String[] args) {
        SpringApplication.run(GuitarApp.class, args);
    }
}
