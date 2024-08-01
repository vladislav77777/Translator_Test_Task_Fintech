package org.innopolis.translationservice.exception;

import lombok.Getter;

@Getter
public class TranslationClientException extends RuntimeException {
    private static final int STATUS_CODE = 400;

    public TranslationClientException(String message) {
        super(message);
    }

    public int getStatusCode() {
        return STATUS_CODE;
    }
}

