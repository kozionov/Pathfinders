package ru.otus.hw.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record UserCreateDto(@NotNull String name,
                            @NotNull String surname,
                            String mobileNumber,
                            String email,
                            @NotEmpty String login,
                            @NotNull String password,
                            @NotNull long roleId,
                            long logId) {

}