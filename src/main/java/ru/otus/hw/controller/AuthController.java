package ru.otus.hw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Контроллер для управления аутентификацией пользователей.
 */
@Controller
@RequestMapping("/auth")
public class AuthController {

    /**
     * Отображает страницу входа.
     */
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}
