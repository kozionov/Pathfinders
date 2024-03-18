package ru.otus.hw.services;

import ru.otus.hw.dto.LogCreateDto;
import ru.otus.hw.entity.Log;

public interface LogService {

    Log insert(long logId, LogCreateDto clubDto);

    long count();
}
