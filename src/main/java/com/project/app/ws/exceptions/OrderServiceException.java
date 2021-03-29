package com.project.app.ws.exceptions;

public class OrderServiceException extends RuntimeException{
    private static final long serialVersionID = 1L;

    public OrderServiceException(String message) {
        super(message);
    }
}
