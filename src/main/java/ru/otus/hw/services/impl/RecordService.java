package ru.otus.hw.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.RecordDto;
import ru.otus.hw.dto.Stat;
import ru.otus.hw.entity.Log;
import ru.otus.hw.entity.Record;
import ru.otus.hw.entity.Score;
import ru.otus.hw.entity.User;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.repositories.LogRepository;
import ru.otus.hw.repositories.RecordRepository;
import ru.otus.hw.repositories.ScoreRepository;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RecordService {

    private final RecordRepository recordRepository;

    private final LogRepository logRepository;

    private final ScoreRepository scoreRepository;

    @Transactional(readOnly = true)
    public RecordDto findById(long id) {
        Optional<Record> record = recordRepository.findById(id);
        if (record.isEmpty()) {
            throw new EntityNotFoundException("Record with id %d not found".formatted(id));
        }
        Record b = record.get();
        return new RecordDto(b.getClassDate(), b.getUser(), b.getScoreSum());
    }

    @Transactional(readOnly = true)
    public List<RecordDto> findByLogId(long logId) {
        Log log = logRepository.findById(logId).orElseThrow(() -> new EntityNotFoundException("Log with id %d not found".formatted(logId)));
        List<Record> allByLog = recordRepository.findAllByLog(log);
        return allByLog.stream().map(b -> new RecordDto(b.getClassDate(), b.getUser(), b.getScoreSum())).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Stat> findByLogIdWithStatistic(long logId) {
        Log log = logRepository.findById(logId).orElseThrow(() -> new EntityNotFoundException("Log with id %d not found".formatted(logId)));
        List<Record> allByLog = recordRepository.findAllByLog(log);
        List<Score> scores = scoreRepository.findAll();
        List<String> scorIds = scores.stream().map(s -> s.getName()).collect(Collectors.toList());
        Map<User, List<Record>> collect = allByLog.stream().collect(Collectors.groupingBy(Record::getUser));
        List<Stat> stats = new ArrayList<>();
        collect.forEach((user, records) -> {

            Map<String, Integer> graph = new HashMap<>();
            scorIds.forEach(s -> graph.put(s, 0));

            records.stream().map(r -> r.getScores()).forEach(s -> {
                s.stream().forEach(x -> {
                    if (scorIds.contains(x.getName())) {
                        int i = graph.get(x.getName()) + 1;
                        graph.replace(x.getName(), i);
                    }
                });
            });
            List<Integer> y = new ArrayList<>();
            graph.forEach((k, v) -> y.add(v));
            stats.add(new Stat(user.getId(), user.getName() + " " + user.getSurname(), scorIds, y));
        });

        return stats;
    }

    @Transactional(readOnly = true)
    public List<RecordDto> findAll() {
        return recordRepository.findAll().stream().map(b -> new RecordDto(b.getClassDate(), b.getUser(), b.getScoreSum())).collect(Collectors.toList());
    }

    @Transactional
    public void deleteById(long id) {
        recordRepository.deleteById(id);
    }

    @Transactional
    public RecordDto insert(Record record) {
        return save(record);
    }

    @Transactional
    public RecordDto update(Record record) {
        return save(record);
    }

    private RecordDto save(Record record) {
        var b = recordRepository.save(record);
        return new RecordDto(b.getClassDate(), b.getUser(), b.getScoreSum());
    }

    @Transactional(readOnly = true)
    public long count() {
        return recordRepository.count();
    }
}
