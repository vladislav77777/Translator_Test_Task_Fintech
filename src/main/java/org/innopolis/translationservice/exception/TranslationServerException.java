package org.innopolis.translationservice.exception;

import lombok.Getter;

@Getter
public class TranslationServerException extends RuntimeException {
    private static final int STATUS_CODE = 500;

    public TranslationServerException(String message) {
        super(message);
    }

    public int getStatusCode() {
        return STATUS_CODE;
    }
}