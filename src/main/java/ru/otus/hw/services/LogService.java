package ru.otus.hw.services;

import ru.otus.hw.dto.LogCreateDto;
import ru.otus.hw.dto.LogDto;
import ru.otus.hw.entity.Log;

import java.util.List;

public interface LogService {

    Log insert(long logId, LogCreateDto clubDto);

    long count();

    List<LogDto> findByClubId(long id);

}
