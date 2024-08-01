package org.innopolis.translationservice;

import org.innopolis.translationservice.configuration.ApplicationConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfiguration.class)
public class TranslationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TranslationServiceApplication.class, args);
    }

}
