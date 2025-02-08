package com.github.syr0ws.crafter.config;

/**
 * Exception thrown when a configuration-related error occurs.
 */
public class ConfigurationException extends Exception {

    public ConfigurationException(String message) {
        super(message);
    }

    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
