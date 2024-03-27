package ru.otus.hw.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.hw.dto.ClubDto;
import ru.otus.hw.dto.LogCreateDto;
import ru.otus.hw.dto.LogDto;
import ru.otus.hw.services.LogService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LogController {

    private final LogService logService;

    @GetMapping("/api/logs/{id}")
    public List<LogDto> getLogByClubId(@PathVariable("id") long id) {
        return logService.findByClubId(id);
    }

    @PutMapping("/api/logs/{id}")
    public ClubDto editLog(@PathVariable("id") long id, @RequestBody @Valid LogCreateDto logCreateDto) {
        logService.insert(id, logCreateDto);
        return new ClubDto();
    }

}
