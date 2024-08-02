package org.innopolis.translationservice.controller;


import lombok.RequiredArgsConstructor;
import org.innopolis.translationservice.service.TranslationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;


@Controller
@RequiredArgsConstructor
public class TranslationController {
    private final TranslationService translationService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/translate")
    public String translate(@RequestParam("text") String text,
                            @RequestParam("sourceLang") String sourceLang,
                            @RequestParam("targetLang") String targetLang,
                            Model model) {
        try {
            String translatedText = translationService.translate(text, sourceLang, targetLang, "userIp"); // Adjust as necessary
            model.addAttribute("translatedText", translatedText);
        } catch (Exception e) {
            String[] words = e.getMessage().split(" ");
            if (words.length > 1) {
                String cleanedErrorMessage = String.join(" ", Arrays.copyOfRange(words, 1, words.length));
                model.addAttribute("error", cleanedErrorMessage);
            } else {
                model.addAttribute("error", e.getMessage());
            }
        }
        model.addAttribute("text", text);
        model.addAttribute("sourceLang", sourceLang);
        model.addAttribute("targetLang", targetLang);
        return "index";
    }
}
