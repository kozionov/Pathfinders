package ru.otus.hw.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.hw.entity.Score;
import ru.otus.hw.services.ScoreService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScoreController {

    private final ScoreService scoreService;

    @GetMapping("/api/scores")
    public List<Score> getAllScores() {
        return scoreService.findAll();
    }

    @GetMapping("/api/scores/{id}")
    public Score getScore(@PathVariable("id") long id) {
        return scoreService.findById(id);
    }

    @PostMapping("/api/scores")
    public Score addScore(@RequestBody @Valid Score bookCreateDto) {
        return scoreService.insert(bookCreateDto);
    }

    @PutMapping("/api/scores/{id}")
    public Score editScore(@PathVariable("id") long id, @RequestBody @Valid Score bookUpdateDto) {
        return scoreService.update(bookUpdateDto);
    }

    @DeleteMapping("/api/scores/{id}")
    public void deleteScore(@PathVariable("id") long id) {
        scoreService.deleteById(id);
    }

}
