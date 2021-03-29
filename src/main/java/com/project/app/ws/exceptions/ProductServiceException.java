package com.project.app.ws.exceptions;

public class ProductServiceException extends RuntimeException{
    private static final long serialVersionID = 1L;

    public ProductServiceException(String message) {
        super(message);
    }
}
