package com.project.app.ws.exceptions;

public class UserServiceException extends RuntimeException{
    private static final long serialVersionID = 1L;

    public UserServiceException(String message) {
        super(message);
    }
}
