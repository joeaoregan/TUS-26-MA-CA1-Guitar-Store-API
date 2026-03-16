package edu.tus.guitarstore.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.tags.Tag;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
	
	@Value("${spring.application.version}")
    private String appVersion;
	
	@Value("${app.author}")
    private String appAuthor;

    @Value("${app.email}")
    private String appEmail;

    @Value("${app.github}")
    private String appGithub;

	@Bean
	OpenAPI guitarStoreOpenAPI() {
		return new OpenAPI()
				.info(new Info().title("Guitar Store Management API")
						.description("A comprehensive RESTful API for managing musical instrument inventory. "
								+ "Developed as part of the Microservices Architecture Module. "
								+ "Key features include N-tier architecture, JPA Auditing, and DTO-based separation.")
						.version(appVersion).contact(new Contact().name(appAuthor).email(appEmail))
						.termsOfService("http://swagger.io/terms/")
						.license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0")))
				.extensions(Map.of("x-keywords", "Spring Boot, REST, Microservices, JPA, Guitar Store"))
				.tags(List.of(
						new Tag().name("Guitar Controller")
								.description("Operations related to guitar inventory management"),
						new Tag().name("Brand Controller")
								.description("Operations for managing instrument manufacturers"),
						new Tag().name("Health Check").description("System monitoring and diagnostic endpoints")))
				.externalDocs(new ExternalDocumentation().description("Guitar Store API Source Code & Project Wiki")
						.url(appGithub));
	}
}