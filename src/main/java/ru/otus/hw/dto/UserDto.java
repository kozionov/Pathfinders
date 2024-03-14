package ru.otus.hw.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    private Long id;

    private String name;

    private String surname;

    private String mobileNumber;

    private String email;

    private String login;

    private String password;

    private String role;

}