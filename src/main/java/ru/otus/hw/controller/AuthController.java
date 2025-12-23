package ru.otus.hw.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.otus.hw.dto.LoginDto;

/**
 * Контроллер для управления аутентификацией пользователей.
 */
@Slf4j
@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    /**
     * Отображает страницу входа.
     */
    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("loginDto", new LoginDto());
        return "login";
    }

    /**
     * Обрабатывает попытку входа пользователя.
     * TODO: Интегрировать с Spring Security после его внедрения.
     */
    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginDto loginDto,
                       BindingResult bindingResult,
                       Model model) {
        
        if (bindingResult.hasErrors()) {
            log.warn("Login validation failed: {}", bindingResult.getAllErrors());
            return "login";
        }

        // TODO: Заменить на настоящую аутентификацию через Spring Security
        if ("dir".equals(loginDto.getLogin()) && "dir".equals(loginDto.getPassword())) {
            log.info("User {} successfully logged in", loginDto.getLogin());
            return "redirect:/";
        }

        model.addAttribute("error", "Неверный логин или пароль");
        log.warn("Failed login attempt for user: {}", loginDto.getLogin());
        return "login";
    }

    /**
     * Обрабатывает выход пользователя.
     */
    @GetMapping("/logout")
    public String logout() {
        log.info("User logged out");
        return "redirect:/auth/login";
    }
}
