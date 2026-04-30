package edu.tus.guitarstore.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import edu.tus.guitarstore.dto.ErrorResponseDto;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

     /**
     * 400 Bad Request handling Handle GuitarAlreadyExistsException.
     * @return ResponseEntity with error details and HTTP status 400
     */
    @ExceptionHandler(GuitarAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleGuitarAlreadyExistsException(GuitarAlreadyExistsException exception,
            final WebRequest webRequest) {
        ErrorResponseDto errorResponseDTO = new ErrorResponseDto(webRequest.getDescription(false),
                HttpStatus.BAD_REQUEST, exception.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
	}

	/**
	 * 400 Bad Request handling Handle BrandAlreadyExistsException.
	 * @return ResponseEntity with error details and HTTP status 400
	 */
	@ExceptionHandler(BrandAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleBrandAlreadyExistsException(
        final BrandAlreadyExistsException exception, final WebRequest webRequest) {
        ErrorResponseDto errorResponseDTO = new ErrorResponseDto(webRequest.getDescription(false),
            HttpStatus.BAD_REQUEST, exception.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
	}

	/**
	 * 404 Not Found handling Handle ResourceNotFoundException.
	 * @return ResponseEntity with error details and HTTP status 404
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException exception,
			WebRequest webRequest) {
		ErrorResponseDto errorResponseDTO = new ErrorResponseDto(webRequest.getDescription(false), HttpStatus.NOT_FOUND, // 404
				exception.getMessage(), LocalDateTime.now());
		return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
	}

	/**
	 * 404 Not Found handling Handle incorrect URL paths / routing errors.
	 * @return ResponseEntity with error details and HTTP status 404
	 */
	@Override
	@Nullable
	protected ResponseEntity<Object> handleNoResourceFoundException(final NoResourceFoundException ex,
			final HttpHeaders headers, final HttpStatusCode status, final WebRequest request) {

		ErrorResponseDto errorResponseDTO = new ErrorResponseDto(request.getDescription(false), HttpStatus.NOT_FOUND,
				"The requested URL path is invalid: " + ex.getMessage(), LocalDateTime.now());

		return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
	}

    /**
	 * 500 Internal Server Error Catch-all exception handling.
	 * @return ResponseEntity with error details and HTTP status 500
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponseDto> handleGlobalException(final Exception exception,
			final WebRequest webRequest) {
		ErrorResponseDto errorResponseDTO = new ErrorResponseDto(webRequest.getDescription(false),
				HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), LocalDateTime.now());
		return new ResponseEntity<>(errorResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * 400 Bad Request handling Handle MethodArgumentNotValidException.
	 * @return ResponseEntity with validation error details
	 * and HTTP status 400
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			final MethodArgumentNotValidException ex,
			final HttpHeaders headers,
			final HttpStatusCode status,
			final WebRequest request) {

		Map<String, String> validationErrors = new HashMap<>();
		List<ObjectError> validationErrorList = ex.getBindingResult().getAllErrors();

		validationErrorList.forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String validationMsg = error.getDefaultMessage();
			validationErrors.put(fieldName, validationMsg);
		});

		return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
	}

	/**
	 * 400 Bad Request handling Handle MethodArgumentTypeMismatchException.
	 * @return ResponseEntity with error details and HTTP status 400
	 * @param exception the exception thrown
	 * when a method argument type mismatch occurs
	 * @param webRequest the current web request
	 * during which the exception was thrown
	 */
	@ExceptionHandler(org.springframework.web.method.annotation.MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorResponseDto> handleTypeMismatchException(
			final MethodArgumentTypeMismatchException exception,
			final WebRequest webRequest) {

		String message = String.format("The parameter '%s' should be of type '%s'", exception.getName(),
				exception.getRequiredType().getSimpleName());

		ErrorResponseDto errorResponseDTO = new ErrorResponseDto(webRequest.getDescription(false),
				HttpStatus.BAD_REQUEST, message, LocalDateTime.now());

		return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
	}
}
