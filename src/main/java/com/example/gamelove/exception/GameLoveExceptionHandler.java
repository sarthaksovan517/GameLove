package com.example.gamelove.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GameLoveExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorMessage> customException(CustomException exception) {
        ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST,
                exception.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(message);
    }

    @ExceptionHandler(UnexpectedException.class)
    public ResponseEntity<ErrorMessage> unexpectedException(UnexpectedException exception) {
        ErrorMessage message = new ErrorMessage(HttpStatus.NO_CONTENT,
                exception.getMessage());

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(message);
    }
}
