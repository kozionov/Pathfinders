package ru.otus.hw.services;

import ru.otus.hw.entity.Score;

import java.util.List;

public interface ScoreService {
    Score findById(long id);

    List<Score> findAll();

    void deleteById(long id);

    Score insert(Score score);

    Score update(Score score);

    long count();
}
