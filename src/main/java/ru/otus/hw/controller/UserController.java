package ru.otus.hw.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.web.bind.annotation.*;
import ru.otus.hw.dto.UserCreateDto;
import ru.otus.hw.dto.UserDto;
import ru.otus.hw.dto.UserUpdateDto;
import ru.otus.hw.services.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/api/users")
    public List<UserDto> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/api/users/{id}")
    public UserDto getUser(@PathVariable("id") long id) {
        return userService.findById(id);
    }

    @PostMapping("/api/users")
    public UserDto addUser(@RequestBody @Valid UserCreateDto bookCreateDto) {
        return userService.insert(bookCreateDto);
    }

    @PutMapping("/api/users/{id}")
    public UserDto editUser(@PathVariable("id") long id, @RequestBody @Valid UserUpdateDto bookUpdateDto) {
        return userService.update(bookUpdateDto);
    }

    @DeleteMapping("/api/users/{id}")
    public void deleteUser(@PathVariable("id") long id) {
        userService.deleteById(id);
    }

    @GetMapping("/api/directors")
    public List<UserDto> getAllDirectors(/*SecurityContext context*/) {
//        if (context.isUserInRole("ADMIN")) {
//            return userService.findAllByRole(1L);
//        } else {
            return userService.findAll();
//        }
    }
}
