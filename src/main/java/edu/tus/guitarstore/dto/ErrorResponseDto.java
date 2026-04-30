package edu.tus.guitarstore.dto;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(name = "ErrorResponse",
description = "Schema to hold error response information")
public class ErrorResponseDto {

    /**
     * API path that was invoked by the client
     * which resulted in an error. This field
     */
	@Schema(description = "API path invoked by client")
	private String apiPath;

	/**
	 * HTTP status code representing the error that occurred.
	 */
	@Schema(description = "Error code representing the error happened")
	private HttpStatus errorCode;

	/**
	 * Error message providing details about the error that occurred.
	 */
	@Schema(description = "Error message representing the error happened")
	private String errorMessage;

	/**
	 * Timestamp indicating when the error occurred.
	 * This can be useful for debugging
	 */
	@Schema(description = "Time representing when the error happened")
	private LocalDateTime errorTime; // Time of error
}
