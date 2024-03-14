package ru.otus.hw.dto;

import jakarta.validation.constraints.NotNull;

public record UserCreateDto(@NotNull String name,
                            @NotNull String surname,
                            String mobileNumber,
                            String email,
                            @NotNull String login,
                            @NotNull String password,
                            @NotNull long roleId) {

}