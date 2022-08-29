package com.exercise.nisum.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NoSuchElementFoundException extends RuntimeException {
    public NoSuchElementFoundException(String message){
        super(String.format("Element with id: %s not found", message));
    }
}
