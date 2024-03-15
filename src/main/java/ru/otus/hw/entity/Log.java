package ru.otus.hw.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "logs")
@NoArgsConstructor
@AllArgsConstructor
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateFrom;

    private LocalDate dateTo;

    @OneToMany
    @JoinTable(
            name="log_record_link",
            joinColumns = @JoinColumn( name="log_id"),
            inverseJoinColumns = @JoinColumn( name="record_id")
    )
    private List<Record> records = new ArrayList<>();


}
