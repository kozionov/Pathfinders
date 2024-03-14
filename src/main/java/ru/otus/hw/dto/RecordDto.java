package ru.otus.hw.dto;

import ru.otus.hw.entity.User;

import java.util.Date;

public record RecordDto(
        Long id,
        User user,
        Date classDate,
        Integer scoreSum) {
}
