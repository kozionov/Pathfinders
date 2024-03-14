package ru.otus.hw.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "clubs")
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
            name="club_user_link",
            joinColumns = @JoinColumn( name="club_id"),
            inverseJoinColumns = @JoinColumn( name="user_id")
    )
    private List<User> members;

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