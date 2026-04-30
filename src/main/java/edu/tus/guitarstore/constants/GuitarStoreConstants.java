package edu.tus.guitarstore.constants;

public final class GuitarStoreConstants {

    private GuitarStoreConstants() {
        // init restricted
    }

    /**
     * Constants for status codes and messages.
     */
    public static final String STATUS_201 = "201";

    /**
     * Message for successful creation of a guitar.
     */
    public static final String MESSAGE_201 = "Guitar created successfully";

    /**
     * Message for successful request processing.
     */
    public static final String STATUS_200 = "200";

    /**
     * Message for successful request processing.
     */
    public static final String MESSAGE_200 = "Request processed successfully";

    /**
     * Status code for bad request errors.
     */
    public static final String STATUS_417 = "417";

    /**
     * * Message for failed update or delete operations.
	 */
    public static final String MESSAGE_417_UPDATE = "Update operation failed. Please try again or contact Dev team";

    /**
     * Message for failed delete operations.
     */
    public static final String MESSAGE_417_DELETE = "Delete operation failed. Please try again or contact Dev team";
}
