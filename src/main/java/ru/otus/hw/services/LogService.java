package ru.otus.hw.services;

import ru.otus.hw.dto.LogCreateDto;
import ru.otus.hw.dto.LogDto;
import ru.otus.hw.entity.Log;

import java.util.List;

public interface LogService {

    Log insert(long logId, LogCreateDto clubDto);

    List<LogDto> findByClubId(long id);

    LogDto findCurrentByClubId(long id);

    Log createNewLog();

    void addUser(long userId);

}
