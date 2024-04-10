package ru.otus.hw.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.entity.Pearl;
import ru.otus.hw.repositories.PearlRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PearlController {

    private final PearlRepository pearlRepository;

    @GetMapping("/api/pearls")
    public List<Pearl> getAll() {
        return pearlRepository.findAll();
    }

}
