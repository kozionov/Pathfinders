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
    public List<LogDto> getAllClubs(@PathVariable("id") long id) {
        return logService.findByClubId(id);
    }
//
//    @GetMapping("/api/logs/{id}")
//    public ClubDto getClub(@PathVariable("id") long id) {
//        return logService.findById(id);
//    }

//    @PostMapping("/api/logs")
//    public ClubDto addRecords(@RequestBody @Valid LogCreateDto logCreateDto) {
//        logService.insert(logCreateDto);
//        return new ClubDto();
//    }

    @PutMapping("/api/logs/{id}")
    public ClubDto editClub(@PathVariable("id") long id, @RequestBody @Valid LogCreateDto logCreateDto) {
        logService.insert(id, logCreateDto);
        return new ClubDto();
    }
//
//    @DeleteMapping("/api/logs/{id}")
//    public void deleteClub(@PathVariable("id") long id) {
//        logService.deleteById(id);
//    }

}
