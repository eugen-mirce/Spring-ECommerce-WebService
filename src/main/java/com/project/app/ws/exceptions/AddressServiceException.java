package com.project.app.ws.exceptions;

public class AddressServiceException extends RuntimeException {
    private static final long serialVersionID = 1L;

    public AddressServiceException(String message) {
        super(message);
    }
}
