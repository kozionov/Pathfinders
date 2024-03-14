package ru.otus.hw.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "logs")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date dateFrom;

    private Date dateTo;

    @OneToMany
    @JoinTable(
            name="log_record_link",
            joinColumns = @JoinColumn( name="log_id"),
            inverseJoinColumns = @JoinColumn( name="record_id")
    )
    private List<Record> records;


}
