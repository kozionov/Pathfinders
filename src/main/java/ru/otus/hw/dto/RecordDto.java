package ru.otus.hw.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.otus.hw.entity.User;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class RecordDto {

    private LocalDate classDate;

    private Long userId;

    private Integer scoreSum;

    public RecordDto(LocalDate classDate, User user, Integer scoreSum) {
        this.classDate = classDate;
        this.userId = user.getId();
        this.scoreSum = scoreSum;
    }

}
