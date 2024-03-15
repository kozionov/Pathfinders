package ru.otus.hw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.entity.Role;
import ru.otus.hw.entity.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByLogin(String login);

    List<User> findAllByRole(Role role);

}
