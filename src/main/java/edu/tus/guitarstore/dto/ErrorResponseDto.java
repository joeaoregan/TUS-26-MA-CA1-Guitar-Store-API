package edu.tus.guitarstore.dto;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponseDto {
	private String apiPath; // URI called by the client
	private HttpStatus errorCode; // HTTP status
	private String errorMessage; // Exception message
	private LocalDateTime errorTime; // Time of error
}
