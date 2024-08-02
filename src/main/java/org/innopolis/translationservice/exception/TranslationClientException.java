package org.innopolis.translationservice.exception;

import lombok.Getter;

@Getter
public class TranslationClientException extends RuntimeException {
    private static final int STATUS_CODE = 400;
    private final String userFriendlyMessage;

    public TranslationClientException(String userFriendlyMessage) {
        super(userFriendlyMessage);
        this.userFriendlyMessage = userFriendlyMessage;
    }

    public String getUserFriendlyMessage() {
        return userFriendlyMessage;
    }

    public int getStatusCode() {
        return STATUS_CODE;
    }
}

