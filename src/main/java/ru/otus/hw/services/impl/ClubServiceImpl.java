package ru.otus.hw.services.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.ClubCreateDto;
import ru.otus.hw.dto.ClubDto;
import ru.otus.hw.dto.ClubUpdateDto;
import ru.otus.hw.entity.Club;
import ru.otus.hw.entity.User;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.repositories.ClubRepository;
import ru.otus.hw.services.ClubService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ClubServiceImpl implements ClubService {

    private final ClubRepository clubRepository;
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
            throw new EntityNotFoundException("Club with id %d not found".formatted(director.getLogin()));
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
        var club = save(0L, clubCreateDto.getName(), clubCreateDto.getCity(), clubCreateDto.getDirector());
        return modelMapper.map(club, ClubDto.class);
    }

    @Transactional
    @Override
    public ClubDto update(ClubUpdateDto clubUpdateDto) {
        var club = save(clubUpdateDto.id(), clubUpdateDto.name(), clubUpdateDto.surname(), clubUpdateDto.mobileNumber(), clubUpdateDto.email(), clubUpdateDto.login(), clubUpdateDto.password(), clubUpdateDto.roleId());
        return modelMapper.map(club, ClubDto.class);
    }

    private ClubDto save(Long id, String name, String surname, String mobileNumber, String email, String login, String password, long roleId) {
        var role = roleRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("Author with id %d not found".formatted(roleId)));
        var club = new Club(id, name, surname, mobileNumber, email, login, new BCryptPasswordEncoder().encode(password), role);
        club = clubRepository.save(club);
        return modelMapper.map(club, ClubDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public long count() {
        return clubRepository.count();
    }
}
