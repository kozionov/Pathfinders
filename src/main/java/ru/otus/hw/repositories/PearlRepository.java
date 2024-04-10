package ru.otus.hw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.entity.Pearl;

import java.util.List;

public interface PearlRepository extends JpaRepository<Pearl, Long> {

    List<Pearl> findAll();
}
