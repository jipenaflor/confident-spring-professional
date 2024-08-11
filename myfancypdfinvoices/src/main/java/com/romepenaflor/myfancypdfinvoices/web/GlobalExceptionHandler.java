package com.romepenaflor.myfancypdfinvoices.web;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// @RestControllerAdvice applies this class to all @Controller and @RestController
// It writes JSON/ XML to the @ResponseBody. In opposite, @ControllerAdvice writes HTML
@RestControllerAdvice
public class GlobalExceptionHandler {
    // When controller fails validating method arguments that are annotated with @Valid
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        return "Sorry, that was not quite right: " + exception.getMessage();
    }

    // Handle @RequestParam validation errors
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public String handleConstraintViolation(ConstraintViolationException exception) {
        return "Sorry, that was not quite right: " + exception.getMessage();
    }
}
