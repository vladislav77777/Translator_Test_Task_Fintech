package org.innopolis.translationservice.model;

import java.time.LocalDateTime;

public record TranslationRequest(
        Long id,
        String ip,
        String input,
        String result,
        LocalDateTime timestamp
) {
}
