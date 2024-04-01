package ru.otus.hw.services.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.ClubCreateDto;
import ru.otus.hw.dto.ClubDto;
import ru.otus.hw.dto.ClubMainDto;
import ru.otus.hw.dto.ClubUpdateDto;
import ru.otus.hw.entity.Club;
import ru.otus.hw.entity.Log;
import ru.otus.hw.entity.Score;
import ru.otus.hw.entity.User;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.repositories.ClubRepository;
import ru.otus.hw.repositories.LogRepository;
import ru.otus.hw.repositories.UserRepository;
import ru.otus.hw.services.ClubService;

import java.time.LocalDate;
import java.time.MonthDay;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ClubServiceImpl implements ClubService {

    private final ClubRepository clubRepository;

    private final UserRepository userRepository;

    private final LogRepository logRepository;

    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    @Override
    public ClubDto findById(long id) {
        Optional<Club> club = clubRepository.findById(id);
        if (club.isEmpty()) {
            throw new EntityNotFoundException("Club with id %d not found".formatted(id));
        }
        return modelMapper.map(club.get(), ClubDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public ClubDto findByDirector(User director) {
        Optional<Club> club = clubRepository.findByDirector(director);
        if (club.isEmpty()) {
            throw new EntityNotFoundException("Club with id %d not found".formatted(director.getId()));
        }
        return modelMapper.map(club.get(), ClubDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ClubDto> findAll() {
        List<Club> clubs = clubRepository.findAll();
        return clubs.stream().map(b -> modelMapper.map(b, ClubDto.class)).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        clubRepository.deleteById(id);
    }

    @Transactional
    @Override
    public ClubDto insert(ClubCreateDto clubCreateDto) {
        Optional<User> director = userRepository.findById(clubCreateDto.getDirectorId());
        if (director.isEmpty()) {
            throw new EntityNotFoundException("Director with ids %s not found".formatted(clubCreateDto.getDirectorId()));
        }
        Log log = createLog();
        var club = save(0L, clubCreateDto.getName(), clubCreateDto.getCity(), director.get(), new ArrayList<>(), List.of(log));
        return modelMapper.map(club, ClubDto.class);
    }

    private Log createLog() {
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

    @Transactional
    @Override
    public ClubDto update(ClubUpdateDto clubUpdateDto) {
        Optional<User> director = userRepository.findById(clubUpdateDto.getDirectorId());
        if (director.isEmpty()) {
            throw new EntityNotFoundException("Director with ids %s not found".formatted(clubUpdateDto.getDirectorId()));
        }
        var club = save(clubUpdateDto.getId(), clubUpdateDto.getName(), clubUpdateDto.getCity(), director.get(), new ArrayList<>(), new ArrayList<>());
        return modelMapper.map(club, ClubDto.class);
    }

    private ClubDto save(Long id, String name, String city, User director, List<Score> scores, List<Log> logs) {
        var club = new Club(id, name, city, director, scores, logs);
        club = clubRepository.save(club);
        return modelMapper.map(club, ClubDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public long count() {
        return clubRepository.count();
    }

    @Transactional(readOnly = true)
    @Override
    public ClubMainDto findByUserId(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("нет пользователя с id"));
        Optional<Club> byDirector = clubRepository.findByDirector(user);
        if (byDirector.isEmpty()) {
            return new ClubMainDto();
        }
        return modelMapper.map(byDirector.get(), ClubMainDto.class);
    }
}
