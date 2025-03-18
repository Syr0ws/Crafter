package com.github.syr0ws.crafter.business;

public class BusinessFailureProcessException extends RuntimeException {

    public BusinessFailureProcessException(String message) {
        super(message);
    }

    public BusinessFailureProcessException(String message, Throwable cause) {
        super(message, cause);
    }
}
