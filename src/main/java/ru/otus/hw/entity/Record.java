package ru.otus.hw.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "records")
@AllArgsConstructor
@NoArgsConstructor
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    private LocalDate classDate;

    private Integer scoreSum;

    @ManyToOne
    @JoinColumn(name = "log_id", referencedColumnName = "id", nullable = false)
    private Log log;

    @OneToMany
    @JoinTable(
            name="record_score_link",
            joinColumns = @JoinColumn( name="record_id"),
            inverseJoinColumns = @JoinColumn( name="score_id")
    )
    private List<Score> scores = new ArrayList<>();
}
