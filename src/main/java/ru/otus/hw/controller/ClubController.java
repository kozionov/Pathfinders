package ru.otus.hw.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.hw.dto.ClubCreateDto;
import ru.otus.hw.dto.ClubDto;
import ru.otus.hw.dto.ClubMainDto;
import ru.otus.hw.dto.ClubUpdateDto;
import ru.otus.hw.services.ClubService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ClubController {

    private final ClubService clubService;

    @GetMapping("/api/clubs")
    public List<ClubDto> getAllClubs() {
        return clubService.findAll();
    }

    @GetMapping("/api/clubs/{id}")
    public ClubDto getClub(@PathVariable("id") long id) {
        return clubService.findById(id);
    }

    @GetMapping("/api/clubs/user/{id}")
    public ClubMainDto getClubByUserId(@PathVariable("id") long id) {
        return clubService.findByUserId(id);
    }

    @PostMapping("/api/clubs")
    public ClubDto addClub(@RequestBody @Valid ClubCreateDto clubCreateDto) {
        return clubService.insert(clubCreateDto);
    }

    @PutMapping("/api/clubs/{id}")
    public ClubDto editClub(@PathVariable("id") long id, @RequestBody @Valid ClubUpdateDto clubUpdateDto) {
        return clubService.update(clubUpdateDto);
    }

    @DeleteMapping("/api/clubs/{id}")
    public void deleteClub(@PathVariable("id") long id) {
        clubService.deleteById(id);
    }

}
