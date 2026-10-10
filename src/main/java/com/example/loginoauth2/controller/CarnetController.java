package com.example.loginoauth2.controller;

import com.example.loginoauth2.service.CarnetService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CarnetController {

    private final CarnetService service;

    public CarnetController(CarnetService service) {
        this.service = service;
    }

    @GetMapping("/concoctions")
    public String liste(Model model, @AuthenticationPrincipal OAuth2User user) {
        model.addAttribute("pseudo", user.getAttribute("login"));
        model.addAttribute("concoctions", service.toutesLesConcoctions());
        return "concoctions";   // → templates/concoctions.html
    }

    @GetMapping("/gestion")
    public String gestion(Model model, @AuthenticationPrincipal OAuth2User user) {
        model.addAttribute("pseudo", user.getAttribute("login"));
        return "gestion";       // → templates/gestion.html
    }
}