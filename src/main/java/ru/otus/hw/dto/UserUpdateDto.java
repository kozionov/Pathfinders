package ru.otus.hw.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserUpdateDto(@NotBlank Long id,
                            @NotNull String name,
                            @NotNull String surname,
                            String mobileNumber,
                            String email,
                            @NotNull String login,
                            @NotNull String password,
                            @NotNull String role) {

}