package com.schindler.springsparkdemo.exception;

import com.schindler.springsparkdemo.exception.exceptions.EmployeeNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmployeeNotExistException.class)
    public ResponseEntity<String> handleEmployeeNotFoundException(final EmployeeNotExistException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
