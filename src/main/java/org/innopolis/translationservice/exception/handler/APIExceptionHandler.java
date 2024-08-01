package org.innopolis.translationservice.exception.handler;

import org.innopolis.translationservice.exception.TranslationClientException;
import org.innopolis.translationservice.exception.TranslationServerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class APIExceptionHandler {

    @ExceptionHandler(TranslationClientException.class)
    public ResponseEntity<String> handleClientException(TranslationClientException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(TranslationServerException.class)
    public ResponseEntity<String> handleServerException(TranslationServerException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

}
