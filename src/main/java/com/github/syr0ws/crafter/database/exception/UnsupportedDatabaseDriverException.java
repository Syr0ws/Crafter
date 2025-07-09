package com.github.syr0ws.crafter.database.exception;

/**
 * Exception thrown when an unsupported or unrecognized database driver is encountered.
 */
public class UnsupportedDatabaseDriverException extends Exception {

    /**
     * @see Exception#Exception(String)
     */
    public UnsupportedDatabaseDriverException(String message) {
        super(message);
    }

    /**
     * @see Exception#Exception(String, Throwable)
     */
    public UnsupportedDatabaseDriverException(String message, Throwable cause) {
        super(message, cause);
    }
}
