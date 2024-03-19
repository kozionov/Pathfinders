package ru.otus.hw.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "clubs")
@NoArgsConstructor
@AllArgsConstructor
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String city;

    @OneToOne
    private User director;

    @OneToMany
    @JoinTable(
            name="club_score_link",
            joinColumns = @JoinColumn( name="club_id"),
            inverseJoinColumns = @JoinColumn( name="score_id")
    )
    private List<Score> scores;

    @OneToMany
    @JoinTable(
            name="club_log_link",
            joinColumns = @JoinColumn( name="club_id"),
            inverseJoinColumns = @JoinColumn( name="log_id")
    )
    private List<Log> log;

}
