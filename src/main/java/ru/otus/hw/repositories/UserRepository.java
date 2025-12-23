package ru.otus.hw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.User;

import java.util.Optional;

/**
 * Репозиторий для работы с пользователями.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Поиск пользователя по логину.
     * 
     * @param login логин пользователя
     * @return Optional с пользователем
     */
    Optional<User> findByLogin(String login);
}
