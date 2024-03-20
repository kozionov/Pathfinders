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
        Log log = logRepository.save(new Log(0L, LocalDate.of(2023, 9, 1), LocalDate.of(2024, 6, 30), new ArrayList<>(), new ArrayList<>()));
        var club = save(0L, clubCreateDto.getName(), clubCreateDto.getCity(), director.get(), new ArrayList<>(), List.of(log));
        return modelMapper.map(club, ClubDto.class);
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
