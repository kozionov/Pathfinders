package ru.otus.hw.services;

import ru.otus.hw.dto.UserCreateDto;
import ru.otus.hw.dto.UserDto;
import ru.otus.hw.dto.UserUpdateDto;

import java.util.List;

public interface UserService {
    UserDto findById(long id);

    List<UserDto> findAll();

    List<UserDto> findAllByRole(long id);

    void deleteById(long id);

    UserDto insert(UserCreateDto userCreateDto);

    UserDto update(UserUpdateDto userUpdateDto);

    long count();

    List<UserDto> findAllByLogId(long id);

}
