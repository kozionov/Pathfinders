package ru.otus.hw.services;

import ru.otus.hw.dto.*;
import ru.otus.hw.entity.Club;
import ru.otus.hw.entity.User;

import java.util.List;

public interface ClubService {
    ClubDto findById(long id);
    ClubDto findByDirector(User director);

    List<ClubDto> findAll();

    void deleteById(long id);

    ClubDto insert(ClubCreateDto clubDto);

    ClubDto update(ClubUpdateDto clubDto);

    long count();
}
