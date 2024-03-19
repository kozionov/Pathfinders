package ru.otus.hw.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.otus.hw.entity.Log;
import ru.otus.hw.entity.Record;

import java.util.List;
import java.util.Optional;

public interface RecordRepository extends CrudRepository<Record, Long> {
    List<Record> findAll();

    Optional<Record> findById(long id);

    List<Record> findAllByLog(Log log);
}
