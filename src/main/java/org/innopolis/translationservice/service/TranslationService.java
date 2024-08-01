package org.innopolis.translationservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.innopolis.translationservice.configuration.ApplicationConfiguration;
import org.innopolis.translationservice.exception.TranslationClientException;
import org.innopolis.translationservice.exception.TranslationServerException;
import org.innopolis.translationservice.model.ResponseErrorBody;
import org.innopolis.translationservice.model.TranslationRequest;
import org.innopolis.translationservice.repository.TranslationRequestRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Service
@RequiredArgsConstructor
public class TranslationService {
    private final RestTemplate restTemplate;
    private final TranslationRequestRepository repository;
    private final ExecutorService executorService;
    private final ObjectMapper objectMapper;
    private final ApplicationConfiguration config;

    public String translate(String input, String sourceLang, String targetLang, String ip) throws ExecutionException, InterruptedException {
        String[] words = input.split(" ");
        List<Future<String>> futures = new ArrayList<>();

        for (String word : words) {
            futures.add(executorService.submit(() -> translateWord(word, sourceLang, targetLang)));
        }
        List<String> translatedWords = new ArrayList<>();
        for (Future<String> future : futures) {
            translatedWords.add(future.get());
        }

        String result = String.join(" ", translatedWords);
        saveRequest(ip, input, result);
        return result;
    }

    private String translateWord(String word, String sourceLang, String targetLang) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-rapidapi-key", config.rapidApi().key());
        headers.set("x-rapidapi-host", "google-translate1.p.rapidapi.com");
        headers.set("Content-Type", "application/x-www-form-urlencoded");


        // Ensure correct encoding of parameters
        String body = String.format("q=%s&source=%s&target=%s",
                encodeValue(word),
                sourceLang,
                targetLang);

        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(config.rapidApi().url(), HttpMethod.POST, entity, String.class);
            JsonNode jsonResponse = objectMapper.readTree(response.getBody());
            return jsonResponse.path("data").path("translations").get(0).path("translatedText").asText();
        } catch (HttpClientErrorException e) {
            ResponseErrorBody response = e.getResponseBodyAs(ResponseErrorBody.class);

            String field;
            try {
                field = response
                        .getError()
                        .getDetails()
                        .getFirst()
                        .getFieldViolations()
                        .getFirst()
                        .getField();
            } catch (NullPointerException ignored) {
                field = "";
            }

            if (field.equals("source")) {
                throw new TranslationClientException("Не найден язык исходного сообщения");
            }
            else if (field.equals("target")) {
                throw new TranslationClientException("Не найден язык переводимого сообщения");
            }
            else {
                throw new TranslationClientException(e.getResponseBodyAsString());
            }

        } catch (HttpServerErrorException e) {
            throw new TranslationServerException("Server error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error: " + e.getMessage(), e);
        }
    }

    private String encodeValue(String value) {
        try {
            return java.net.URLEncoder.encode(value, "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException("Encoding error: " + e.getMessage(), e);
        }
    }
    private void saveRequest(String ip, String input, String result) {
        TranslationRequest request = new TranslationRequest(null, ip, input, result, LocalDateTime.now());
        repository.save(request);
    }
}
