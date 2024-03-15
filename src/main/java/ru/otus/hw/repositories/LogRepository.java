package ru.otus.hw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.entity.Log;

public interface LogRepository extends JpaRepository<Log, Long> {

}
