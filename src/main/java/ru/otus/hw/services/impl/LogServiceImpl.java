package ru.otus.hw.services.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.LogCreateDto;
import ru.otus.hw.dto.LogDto;
import ru.otus.hw.dto.RecordDto;
import ru.otus.hw.entity.Record;
import ru.otus.hw.entity.*;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.repositories.*;
import ru.otus.hw.security.UserPrincipal;
import ru.otus.hw.services.LogService;

import java.time.LocalDate;
import java.time.MonthDay;
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
    public List<LogDto> findByClubId(long clubId) {
        return clubRepository.findById(clubId).orElseThrow(() -> {
                    throw new EntityNotFoundException("Club with ids %s not found".formatted(clubId));
                })
                .getLog()
                .stream()
                .map(x -> modelMapper.map(x, LogDto.class)).toList();
    }

    @Transactional
    @Override
    public LogDto findCurrentByClubId(long clubId) {
        Club club = clubRepository.findById(clubId).orElseThrow(() -> {
            throw new EntityNotFoundException("Club with ids %s not found".formatted(clubId));
        });
        Optional<Log> logByClubId = club.getLog()
                .stream()
                .filter(l -> isActiveLog(l.getDateFrom(), l.getDateTo()))
                .findFirst();
        if (logByClubId.isPresent()) {
            return modelMapper.map(logByClubId.get(), LogDto.class);
        } else {
            Log newLog = createNewLog();
            club.getLog().add(newLog);
            clubRepository.save(club);
            return modelMapper.map(newLog, LogDto.class);
        }
    }

    public Log createNewLog() {
        LocalDate currentDate = LocalDate.now();
        LocalDate startDate;
        LocalDate endDate;
        if (isFirstHalfOfYear(currentDate)) {
            startDate = LocalDate.of(currentDate.getYear() - 1, 7, 15);
            endDate = LocalDate.of(currentDate.getYear(), 7, 14);
        } else {
            startDate = LocalDate.of(currentDate.getYear(), 7, 15);
            endDate = LocalDate.of(currentDate.getYear() + 1, 7, 14);
        }
        return logRepository.save(new Log(0L, startDate, endDate, new ArrayList<>(), new ArrayList<>()));
    }

    @Transactional
    @Override
    public void addUser(long userId) {
        UserPrincipal dir = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User director = userRepository.findById(dir.getId()).get();
        Log activeLog = clubRepository.findByDirector(director).get().getLog()
                .stream()
                .filter(l -> isActiveLog(l.getDateFrom(), l.getDateTo()))
                .findFirst().get();
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new EntityNotFoundException("User with id %s not found".formatted(userId));
        });
        if (activeLog.getMembers().stream().anyMatch(u -> u.getId().intValue() == user.getId().intValue())) {
            throw new RuntimeException("Пользователь уже есть в этом журнале");
        } else {
            activeLog.getMembers().add(user);
        }
    }

    public static boolean isFirstHalfOfYear(LocalDate currentDate) {
        return isBetween(currentDate.getMonthValue(), currentDate.getDayOfMonth(), 1, 1, 7, 14);
    }

    public static boolean isBetween(
            int currentMonth, int currentDay,
            int fromMonth, int fromDay,
            int untilMonth, int untilDay) {
        MonthDay current = MonthDay.of(currentMonth, currentDay);
        MonthDay from = MonthDay.of(fromMonth, fromDay);
        MonthDay until = MonthDay.of(untilMonth, untilDay);

        if (from.compareTo(until) <= 0) {
            return from.compareTo(current) <= 0 &&
                    current.compareTo(until) <= 0;
        } else {
            return current.compareTo(until) <= 0 ||
                    current.compareTo(from) >= 0;
        }
    }

    private boolean isActiveLog(LocalDate from, LocalDate to) {
        return LocalDate.now().isAfter(from) && LocalDate.now().isBefore(to);
    }
}
