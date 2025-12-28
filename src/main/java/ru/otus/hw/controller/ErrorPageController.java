package ru.otus.hw.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class ErrorPageController {

    @GetMapping("/error/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String unauthorized() {
        return "error/401";
    }

    @GetMapping("/error/403")
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String forbidden() {
        return "error/403";
    }
}
