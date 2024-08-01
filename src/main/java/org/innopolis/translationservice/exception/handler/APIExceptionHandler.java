package org.innopolis.translationservice.exception.handler;

import org.innopolis.translationservice.exception.TranslationClientException;
import org.innopolis.translationservice.exception.TranslationServerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

@RestControllerAdvice
public class APIExceptionHandler {

    @ExceptionHandler(TranslationClientException.class)
    public ModelAndView handleClientException(TranslationClientException ex) {
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("translatedText", ex.getMessage());
        return mav;
    }

    @ExceptionHandler(TranslationServerException.class)
    public ResponseEntity<String> handleServerException(TranslationServerException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

}
