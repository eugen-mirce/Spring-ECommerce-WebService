package com.project.app.ws.exceptions;

public class InvoiceNotFoundException extends RuntimeException{
    private static final long serialVersionID = 1L;

    public InvoiceNotFoundException(String message) {
        super(message);
    }
}
