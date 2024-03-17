package ru.otus.hw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.entity.Score;

public interface ScoreRepository extends JpaRepository<Score, Long> {


}
