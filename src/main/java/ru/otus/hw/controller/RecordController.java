package ru.otus.hw.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.hw.dto.RecordDto;
import ru.otus.hw.entity.Record;
import ru.otus.hw.services.impl.RecordService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;

    @GetMapping("/api/records")
    public List<RecordDto> getAllRecords() {
        return recordService.findAll();
    }

    @GetMapping("/api/records/{id}")
    public RecordDto getRecord(@PathVariable("id") long id) {
        return recordService.findById(id);
    }

    @GetMapping("/api/records/log/{id}")
    public List<RecordDto> getRecordByLog(@PathVariable("id") long id) {
        return recordService.findByLogId(id);
    }

    @PostMapping("/api/records")
    public RecordDto addRecord(@RequestBody @Valid Record bookCreateDto) {
        return recordService.insert(bookCreateDto);
    }

    @PutMapping("/api/records/{id}")
    public RecordDto editRecord(@PathVariable("id") long id, @RequestBody @Valid Record bookUpdateDto) {
        return recordService.update(bookUpdateDto);
    }

    @DeleteMapping("/api/records/{id}")
    public void deleteRecord(@PathVariable("id") long id) {
        recordService.deleteById(id);
    }

}
