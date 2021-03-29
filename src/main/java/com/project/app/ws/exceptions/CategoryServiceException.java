package com.project.app.ws.exceptions;

public class CategoryServiceException extends RuntimeException{
    private static final long serialVersionID = 1L;

    public CategoryServiceException(String message) {
        super(message);
    }
}
