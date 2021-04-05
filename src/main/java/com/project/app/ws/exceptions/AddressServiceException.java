package com.project.app.ws.exceptions;

import com.project.app.ws.io.repositories.AddressRepository;

public class AddressServiceException extends RuntimeException {
    private static final long serialVersionID = 1L;

    public AddressServiceException(String message) {
        super(message);
    }
}
