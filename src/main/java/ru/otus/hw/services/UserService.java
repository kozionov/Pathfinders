package ru.otus.hw.services;

import ru.otus.hw.dto.*;

import java.util.List;

public interface UserService {
    UserDto findById(long id);

    List<UserDto> findAll();

    void deleteById(long id);

    UserDto insert(UserCreateDto userCreateDto);

    UserDto update(UserUpdateDto userUpdateDto);

    long count();
}
