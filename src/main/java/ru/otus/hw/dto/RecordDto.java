package ru.otus.hw.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.otus.hw.entity.User;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class RecordDto {

    private LocalDate classDate;

    private Long userId;

    private Integer scoreSum;

    private List<Integer> scoreIds;

    public RecordDto(LocalDate classDate, User user, Integer scoreSum) {
        this.classDate = classDate;
        this.userId = user.getId();
        this.scoreSum = scoreSum;
    }

    public RecordDto(LocalDate classDate, Long userId, Integer scoreSum, List<Integer> scoreIds) {
        this.classDate = classDate;
        this.userId = userId;
        this.scoreSum = scoreSum;
        this.scoreIds = scoreIds;
    }
}
