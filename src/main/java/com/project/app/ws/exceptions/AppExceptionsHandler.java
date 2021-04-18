package com.project.app.ws.exceptions;

import com.project.app.ws.ui.model.response.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import java.util.Date;

@ControllerAdvice
public class AppExceptionsHandler {

    @ExceptionHandler(
            value = {   UserServiceException.class,
                        AddressServiceException.class,
                        ProductServiceException.class,
                        OrderServiceException.class,
                        InvoiceServiceException.class,
                        CategoryServiceException.class
            })
    public ResponseEntity<Object> handleUserServiceException(Exception ex, WebRequest request) {
        ErrorMessage errorMessage =
                new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),new Date(),ex.getMessage());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
