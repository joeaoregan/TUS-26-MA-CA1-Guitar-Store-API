package edu.tus.guitarstore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class GuitarAlreadyExistsException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * GuitarAlreadyExistsException constructor with message parameter.
     * @param message
     */
    public GuitarAlreadyExistsException(final String message) {
        super(message);
    }
}
