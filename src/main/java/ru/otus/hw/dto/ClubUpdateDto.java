package ru.otus.hw.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClubUpdateDto {

    private Long id;

    private String name;

    private String city;

    private Long directorId;

    private List<Long> membersId;

    private List<Long> scoresId;

    private List<Long> logsId;
}
