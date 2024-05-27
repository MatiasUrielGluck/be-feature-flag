package com.matiasgluck.befeatureflag.exception;

import org.hibernate.PropertyValueException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value
            = { JwtFilterException.class })
    protected ResponseEntity<Object> handleSignatureException(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Invalid token.";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(value
            = { PropertyValueException.class })
    protected ResponseEntity<Object> handleBadRequest(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Bad request.";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value
            = { UserAlreadyExistsException.class })
    protected ResponseEntity<Object> handleUserAlreadyExists(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "User already exists.";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}