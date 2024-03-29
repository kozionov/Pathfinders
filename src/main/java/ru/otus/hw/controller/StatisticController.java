package ru.otus.hw.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.dto.Stat;
import ru.otus.hw.services.impl.RecordService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StatisticController {

    private final RecordService recordService;

    @GetMapping("/api/stat/log/{id}")
    public List<Stat> getRecordByLogWithStat(@PathVariable("id") long id) {
        return recordService.findByLogIdWithStatistic(id);
    }

}
