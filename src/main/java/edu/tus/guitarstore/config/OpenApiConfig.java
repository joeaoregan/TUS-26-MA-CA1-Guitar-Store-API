package edu.tus.guitarstore.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI guitarStoreOpenAPI() {
		return new OpenAPI().info(new Info().title("Guitar Store Management API")
				.description("A comprehensive RESTful API for managing musical instrument inventory. "
						+ "Developed as part of the Microservices Architecture Module. "
						+ "Key features include N-tier architecture, JPA Auditing, and DTO-based separation.")
				.version("v1.0.0").contact(new Contact().name("Joe O'Regan").email("A00258304@student.tus.ie"))
				.license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0")));
	}
}