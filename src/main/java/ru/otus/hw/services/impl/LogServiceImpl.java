package ru.otus.hw.services.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.LogCreateDto;
import ru.otus.hw.dto.LogDto;
import ru.otus.hw.dto.RecordDto;
import ru.otus.hw.entity.Log;
import ru.otus.hw.entity.Record;
import ru.otus.hw.entity.Score;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.repositories.*;
import ru.otus.hw.services.LogService;

import java.time.LocalDate;
import java.util.ArrayList;
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
    private final ScoreRepository scoreRepository;

    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public Log insert(long logId, LogCreateDto clubDto) {
        Optional<Log> logById = logRepository.findById(logId);
        if (logById.isEmpty()) {
            throw new EntityNotFoundException("Log with ids %s not found".formatted(logId));
        }
        Log log = logById.get();

        List<Score> scores = scoreRepository.findAll();
        List<Record> records = new ArrayList<>();
        for (RecordDto dto : clubDto.records()) {
            List<Score> scoresToSave = new ArrayList<>();
            dto.getScoreIds().stream().forEach(rs -> scoresToSave.add(scores.stream().filter(q -> q.getId().intValue() == rs.intValue()).findFirst().get()));
            Record record = new Record(0L, userRepository.findById(dto.getUserId()).get(), clubDto.classDate(), dto.getScoreSum(), log, scoresToSave);
            records.add(record);
        }
        List<Record> collect = records.stream().map(x -> recordRepository.save(x)).collect(Collectors.toList());
        return log;
    }

    @Transactional(readOnly = true)
    @Override
    public List<LogDto> findByClubId(long id) {
        return clubRepository.findById(id).orElseThrow()
                .getLog()
                .stream()
                .map(x -> modelMapper.map(x, LogDto.class)).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public LogDto findCurrentByClubId(long id) {
        return clubRepository.findById(id).orElseThrow()
                .getLog()
                .stream()
                .filter(l -> isActiveLog(l.getDateFrom(), l.getDateTo()))
                .findFirst()
                .map(x -> modelMapper.map(x, LogDto.class))
                .orElseThrow(() -> {
                    throw new EntityNotFoundException("Активных журналов для клуба с id = %s не найдено".formatted(id));
                });
    }

    private boolean isActiveLog(LocalDate from, LocalDate to) {
        return LocalDate.now().isAfter(from) && LocalDate.now().isBefore(to);
    }
}
