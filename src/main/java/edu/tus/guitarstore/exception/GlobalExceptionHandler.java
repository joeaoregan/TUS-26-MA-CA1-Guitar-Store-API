package edu.tus.guitarstore.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import edu.tus.guitarstore.dto.ErrorResponseDto;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(GuitarAlreadyExistsException.class)
			public ResponseEntity<ErrorResponseDto> handleGuitarAlreadyExistsException(GuitarAlreadyExistsException exception, WebRequest webRequest) {
		ErrorResponseDto errorResponseDTO = new ErrorResponseDto(
				webRequest.getDescription(false),
				HttpStatus.BAD_REQUEST,
				exception.getMessage(),
				LocalDateTime.now()
				);
		return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
	}
	
	// 404 Not Found    
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest) {
	    ErrorResponseDto errorResponseDTO = new ErrorResponseDto(
	            webRequest.getDescription(false),
	            HttpStatus.NOT_FOUND, // 404
	            exception.getMessage(),
	            LocalDateTime.now()
	    );
	    return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
	}
	
	// Handle incorrect URL paths / routing errors
	@ExceptionHandler(org.springframework.web.servlet.resource.NoResourceFoundException.class)
	public ResponseEntity<ErrorResponseDto> handleNoResourceFoundException(
	        org.springframework.web.servlet.resource.NoResourceFoundException exception, WebRequest webRequest) {
	    ErrorResponseDto errorResponseDTO = new ErrorResponseDto(
	            webRequest.getDescription(false),
	            HttpStatus.NOT_FOUND, // Force 404 for bad URLs
	            "The requested URL path is invalid: " + exception.getMessage(),
	            LocalDateTime.now()
	    );
	    return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
	}
	
	// 500 Internal Server Error
	@ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGlobalException(Exception exception, WebRequest webRequest) {
        ErrorResponseDto errorResponseDTO = new ErrorResponseDto(
                webRequest.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
