package com.github.syr0ws.crafter.business;

/**
 * Exception thrown to indicate an error occurred during the processing of a {@link BusinessFailure}.
 */
public class BusinessFailureProcessException extends RuntimeException {

    /**
     * @see RuntimeException#RuntimeException(String)
     */
    public BusinessFailureProcessException(String message) {
        super(message);
    }

    /**
     * @see RuntimeException#RuntimeException(String, Throwable)
     */
    public BusinessFailureProcessException(String message, Throwable cause) {
        super(message, cause);
    }
}
