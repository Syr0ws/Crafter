package com.github.syr0ws.crafter.nms;

/**
 * Thrown when a corresponding {@link ServerVersion} cannot be determined.
 */
public class ServerVersionNotFoundException extends Exception {

    /**
     * @see Exception#Exception(String)
     */
    public ServerVersionNotFoundException(String message) {
        super(message);
    }

    /**
     * @see Exception#Exception(String, Throwable)
     */
    public ServerVersionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
