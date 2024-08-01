package org.innopolis.translationservice.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfiguration(RapidApi rapidApi) {
    public record RapidApi(
            String url,
            String key
    ) {
    }
}
