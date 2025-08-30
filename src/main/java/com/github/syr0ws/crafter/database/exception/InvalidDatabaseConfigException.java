package com.github.syr0ws.crafter.database.exception;

public class InvalidDatabaseConfigException extends Exception {

    public InvalidDatabaseConfigException(String message) {
        super(message);
    }

    public InvalidDatabaseConfigException(String message, Throwable cause) {
        super(message, cause);
    }
}
