package org.innopolis.translationservice.service;

import lombok.RequiredArgsConstructor;
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
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

//Вход
//
//en → ru
//
//Hello world, this is my first program
//
//Выход
//
//Пример 1: http 200 Привет мир, это является мой первый программа
//
//Пример 2: http 400 Не найден язык исходного сообщения
//
//Пример 3: http 400 Ошибка доступа к ресурсу перевода

@Service
@RequiredArgsConstructor
public class TranslationService {
    private final RestTemplate restTemplate;
    private final TranslationRequestRepository repository;
    private final ExecutorService executorService;

    private final String API_URL = "CHANGE";
    private final String API_KEY = "CHANGE";

    public String translate(String input, String sourceLang, String targetLang, String ip) throws ExecutionException, InterruptedException {
        String[] words = input.split(" ");
        List<String> translatedWords =
                executorService.submit(() -> translateWords(words, sourceLang, targetLang)).get();

        String result = String.join(" ", translatedWords);
        saveRequest(ip, input, result);
        return result;
    }

    private List<String> translateWords(String[] words, String sourceLang, String targetLang) {
        return Arrays.stream(words)
                .map(word -> {
                    try {
                        return translateWord(word, sourceLang, targetLang);
                    } catch (Exception e) {
                        return handleTranslationError(e);
                    }
                }).toList();
    }

    private String translateWord(String word, String sourceLang, String targetLang) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + API_KEY);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        String url = String.format("%s?text=%s&source=%s&target=%s", API_URL, word, sourceLang, targetLang);
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Client error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString(), e);
        } catch (HttpServerErrorException e) {
            throw new RuntimeException("Server error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error: " + e.getMessage(), e);
        }
    }

    private String handleTranslationError(Exception e) {
        if (e instanceof HttpClientErrorException) {
            HttpClientErrorException clientError = (HttpClientErrorException) e;
            switch (clientError.getStatusCode().value()) {
                case 400:
                    return "Ошибка доступа к ресурсу перевода";
                default:
                    return "Ошибка клиента: " + clientError.getMessage();
            }
        } else if (e instanceof HttpServerErrorException) {
            return "Ошибка сервера: " + e.getMessage();
        } else {
            return "Неизвестная ошибка: " + e.getMessage();
        }
    }


    private void saveRequest(String ip, String input, String result) {
        TranslationRequest request = new TranslationRequest(null, ip, input, result, LocalDateTime.now());
        repository.save(request);
    }
}

