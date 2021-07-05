package com.example.mongo.apierror;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(CustomException.class)
    public ResponseEntity<String> customExceptionHandle(CustomException ex, WebRequest request) {

        return new ResponseEntity<>(ex.getMessage(), ex.getStatus());
    
    }
    
}
