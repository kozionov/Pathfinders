package ru.otus.hw.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.otus.hw.entity.Log;
import ru.otus.hw.entity.Score;
import ru.otus.hw.entity.User;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClubDto {

    private Long id;

    private String name;

    private String city;

    private User director;

    private List<User> members;

    private List<Score> scores;

    private List<Log> log;
}
