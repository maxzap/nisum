package com.exercise.nisum.handler;

import com.exercise.nisum.exception.ExceptionResponse;
import com.exercise.nisum.exception.NoSuchElementFoundException;
import com.exercise.nisum.exception.ResourceAlreadyExistsException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    public final ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest req) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);

        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    @Override
    public final ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest req) {
        var exceptionResponse = getExceptionResponse(ex, req);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex, WebRequest req) {
        var exceptionResponse = getExceptionResponse(ex, req);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public final ResponseEntity<ExceptionResponse> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex, WebRequest req) {
        var exceptionResponse = getExceptionResponse(ex, req);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }
    @ExceptionHandler(BadCredentialsException.class)
    public final ResponseEntity<ExceptionResponse> handleResourceAlreadyExistsException(BadCredentialsException ex, WebRequest req) {
        var exceptionResponse = getExceptionResponse(ex, req);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public final ResponseEntity<ExceptionResponse> handleResourceAlreadyExistsException(InternalAuthenticationServiceException ex, WebRequest req) {
        var exceptionResponse = getExceptionResponse(ex, req);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoSuchElementFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleNoSuchElementFoundException(NoSuchElementFoundException ex, WebRequest req) {
        var exceptionResponse = getExceptionResponse(ex, req);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    private static ExceptionResponse getExceptionResponse(Exception ex, WebRequest req) {
        return new ExceptionResponse(
                new Date(),
                ex.getMessage(),
                req.getDescription(false)
        );
    }
}
