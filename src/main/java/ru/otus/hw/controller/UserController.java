package ru.otus.hw.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.otus.hw.dto.UserCreateDto;
import ru.otus.hw.dto.UserDto;
import ru.otus.hw.dto.UserUpdateDto;
import ru.otus.hw.security.UserPrincipal;
import ru.otus.hw.services.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/api/users")
    public List<UserDto> getAllUsers() {
        return userService.findAll();
    }
    @GetMapping("/api/users/log/{id}")
    public List<UserDto> getAllUsersByLogId(@PathVariable("id") long id) {
        return userService.findAllByLogId(id);
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
    public List<UserDto> getAllDirectors() {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal.getRole().equals("ADMIN")) {
            return userService.findAllByRole(2L);
        } else {
            return userService.findAllByRole(2L).stream().filter(d -> d.getLogin().equals(principal.getUsername())).collect(Collectors.toList());
        }
    }
}
