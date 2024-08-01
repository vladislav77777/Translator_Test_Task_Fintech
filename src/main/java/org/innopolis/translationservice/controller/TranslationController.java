package org.innopolis.translationservice.controller;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.innopolis.translationservice.service.TranslationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.ExecutionException;


@Controller
@RequiredArgsConstructor
public class TranslationController {
    private final TranslationService translationService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/translate")
    public String translate(@RequestParam String text, @RequestParam String sourceLang, @RequestParam String targetLang, HttpServletRequest request, Model model) throws ExecutionException, InterruptedException {
        String ip = request.getRemoteAddr();
        String translatedText = translationService.translate(text, sourceLang, targetLang, ip);
        model.addAttribute("translatedText", translatedText);
        return "index";
    }
}
