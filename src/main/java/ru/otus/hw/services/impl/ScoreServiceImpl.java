package ru.otus.hw.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.entity.Score;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.repositories.ScoreRepository;
import ru.otus.hw.services.ScoreService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ScoreServiceImpl implements ScoreService {

    private final ScoreRepository scoreRepository;

    @Transactional(readOnly = true)
    @Override
    public Score findById(long id) {
        Optional<Score> score = scoreRepository.findById(id);
        if (score.isEmpty()) {
            throw new EntityNotFoundException("Score with id %d not found".formatted(id));
        }
        return score.get();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Score> findAll() {
        return scoreRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        scoreRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Score insert(Score score) {
        return save(score);
    }

    @Transactional
    @Override
    public Score update(Score score) {
        return save(score);
    }

    private Score save(Score score) {
        score = scoreRepository.save(score);
        return score;
    }

    @Transactional(readOnly = true)
    @Override
    public long count() {
        return scoreRepository.count();
    }
}
