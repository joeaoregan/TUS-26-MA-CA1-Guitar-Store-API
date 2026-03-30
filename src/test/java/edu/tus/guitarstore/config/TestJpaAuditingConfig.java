package edu.tus.guitarstore.config;

import java.util.Optional;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.AuditorAware;

@TestConfiguration
public class TestJpaAuditingConfig {

	@Bean
	@Primary
	public AuditorAware<String> auditAwareImpl() { // Match the bean name expected!
		return () -> Optional.of("test-user");
	}
}