package ru.otus.hw.services.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.LogCreateDto;
import ru.otus.hw.dto.LogDto;
import ru.otus.hw.entity.Log;
import ru.otus.hw.entity.Record;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.repositories.ClubRepository;
import ru.otus.hw.repositories.LogRepository;
import ru.otus.hw.repositories.RecordRepository;
import ru.otus.hw.repositories.UserRepository;
import ru.otus.hw.services.LogService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;

    private final UserRepository userRepository;

    private final RecordRepository recordRepository;

    private final ClubRepository clubRepository;

    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public Log insert(long logId, LogCreateDto clubDto) {
        Optional<Log> logById = logRepository.findById(logId);
        if (logById.isEmpty()) {
            throw new EntityNotFoundException("Log with ids %s not found".formatted(logId));
        }
        Log log = logById.get();
        List<Record> records = clubDto.records()
                .stream()
                .map(x -> new Record(0L, userRepository.findById(x.getUserId()).get(), clubDto.classDate(), x.getScoreSum(), log,null))
                .collect(Collectors.toList());
        List<Record> collect = records.stream().map(x -> recordRepository.save(x)).collect(Collectors.toList());
        return log;
    }

    @Transactional(readOnly = true)
    @Override
    public long count() {
        return 0;
    }

    @Transactional(readOnly = true)
    @Override
    public List<LogDto> findByClubId(long id) {
        return clubRepository.findById(id).orElseThrow()
                .getLog()
                .stream()
                .filter(l -> testDate(l.getDateFrom(), l.getDateTo()))
                .map(x -> modelMapper.map(x, LogDto.class)).toList();
    }

    private boolean testDate(LocalDate from, LocalDate to) {
        return LocalDate.now().isAfter(from) && LocalDate.now().isBefore(to);
    }
}
