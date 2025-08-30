package com.github.syr0ws.crafter.database.exception;

/**
 * Exception thrown to indicate that a database configuration is invalid.
 */
public class InvalidDatabaseConfigException extends Exception {

    /**
     * @see Exception#Exception(String)
     */
    public InvalidDatabaseConfigException(String message) {
        super(message);
    }

    /**
     * @see Exception#Exception(String, Throwable)
     */
    public InvalidDatabaseConfigException(String message, Throwable cause) {
        super(message, cause);
    }
}
