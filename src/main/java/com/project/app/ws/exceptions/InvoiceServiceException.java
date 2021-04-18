package com.project.app.ws.exceptions;

public class InvoiceServiceException extends RuntimeException{
    private static final long serialVersionID = 1L;

    public InvoiceServiceException(String message) {
        super(message);
    }
}
