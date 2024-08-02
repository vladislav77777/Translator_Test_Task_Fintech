package org.innopolis.translationservice.exception;

import lombok.Getter;

@Getter
public class TranslationServerException extends RuntimeException {
    private static final int STATUS_CODE = 500;
    private final String userFriendlyMessage;

    public TranslationServerException(String userFriendlyMessage) {
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