package org.innopolis.translationservice.service;

import org.innopolis.translationservice.configuration.ApplicationConfiguration;
import org.innopolis.translationservice.exception.TranslationServerException;
import org.innopolis.translationservice.repository.TranslationRequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TranslationServiceTest {
    @Mock
    private RestTemplate restTemplate;

    @Mock
    private TranslationRequestRepository repository;

    @Mock
    private ExecutorService executorService;

    @Mock
    private ApplicationConfiguration config;

    @InjectMocks
    private TranslationService translationService;

    private ApplicationConfiguration.RapidApi rapidApi;

    @BeforeEach
    public void setUp() {
        rapidApi = new ApplicationConfiguration.RapidApi("https://mock-api.com", "mock-key");
        when(config.rapidApi()).thenReturn(rapidApi);
    }


    @Test
    void testTranslate_ClientError() {
        String input = "Hello world";
        String sourceLang = "enn";
        String targetLang = "ru";
        String ip = "127.0.0.1";

        when(executorService.submit(any(Callable.class))).thenAnswer(invocation -> {
            Callable<List<String>> callable = invocation.getArgument(0);
            return CompletableFuture.completedFuture(callable.call());
        });

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-rapidapi-key", rapidApi.key());
        headers.set("x-rapidapi-host", "google-translate1.p.rapidapi.com");
        headers.set("Content-Type", "application/x-www-form-urlencoded");

        HttpEntity<String> entity = new HttpEntity<>("q=Hello&source=enn&target=ru", headers);

        doThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Bad Request"))
                .when(restTemplate).exchange(eq(rapidApi.url()), eq(HttpMethod.POST), eq(entity), eq(String.class));

        assertThrows(IllegalStateException.class, () -> {
            translationService.translate(input, sourceLang, targetLang, ip);
        });
    }

    @Test
    void testTranslate_ServerError() {
        String input = "Hello world";
        String sourceLang = "en";
        String targetLang = "ru";
        String ip = "127.0.0.1";

        when(executorService.submit(any(Callable.class))).thenAnswer(invocation -> {
            Callable<List<String>> callable = invocation.getArgument(0);
            return CompletableFuture.completedFuture(callable.call());
        });

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-rapidapi-key", rapidApi.key());
        headers.set("x-rapidapi-host", "google-translate1.p.rapidapi.com");
        headers.set("Content-Type", "application/x-www-form-urlencoded");

        HttpEntity<String> entity = new HttpEntity<>("q=Hello&source=en&target=ru", headers);

        doThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error"))
                .when(restTemplate).exchange(eq(rapidApi.url()), eq(HttpMethod.POST), eq(entity), eq(String.class));

        Exception exception = assertThrows(TranslationServerException.class, () -> {
            translationService.translate(input, sourceLang, targetLang, ip);
        });

        assertEquals("Server error: 500 INTERNAL_SERVER_ERROR - ", exception.getMessage());
    }
}
