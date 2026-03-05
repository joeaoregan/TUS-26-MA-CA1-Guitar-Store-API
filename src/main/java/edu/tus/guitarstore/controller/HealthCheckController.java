package edu.tus.guitarstore.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Health Check", description = "Utility endpoints to verify system status")
@RestController
public class HealthCheckController {

	@Operation(summary = "Sanity Test", description = "Simple endpoint to verify the DispatcherServlet is routing requests properly")
	@GetMapping("/hello")
	public String sayHello() {
		return "Guitar Manager API is up and running!";
	}
}