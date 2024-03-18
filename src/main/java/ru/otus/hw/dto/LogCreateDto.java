package ru.otus.hw.dto;

import java.time.LocalDate;
import java.util.List;

public record LogCreateDto(

        LocalDate classDate,

        List<RecordDto> records) {
}
