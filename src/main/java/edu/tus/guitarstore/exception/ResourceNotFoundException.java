package edu.tus.guitarstore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * resourceName: The name of the resource
     * that was not found (e.g., "Guitar", "Brand").
     * fieldName: The name of the field used to search for the resource.
     * fieldValue: The value of the field used to search for the resource.
     */
    private String resourceName;

    /**
     * fieldName: The name of the field used to search for the resource.
     */
    private String fieldName;

    /**
     * fieldValue: The value of the field used to search for the resource.
     */
    private String fieldValue;

    /**
     * Resource not found exception constructor.
     * @param resourceName
     * @param fieldName
     * @param fieldValue
     */
    public ResourceNotFoundException(final String resourceName, final String fieldName, final String fieldValue) {
        super(String.format("%s not found with the given input data %s : '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    /**
     * getResourceName: Get the name of the resource that was not found.
     * @return The name of the resource that was not found.
     */
    public String getResourceName() {
        return this.resourceName;
    }

    /**
     * getFieldName: Get the name of the field used to search for the resource.
     * @return The name of the field used to search for the resource.
     */
    public String getFieldName() {
        return this.fieldName;
    }

    /**
     * getFieldValue: Get the value of the field
     * used to search for the resource.
     * @return The value of the field used to search for the resource.
     */
    public String getFieldValue() {
        return this.fieldValue;
    }
}
