package ru.otus.hw.services;

import ru.otus.hw.entity.Role;
import ru.otus.hw.models.Author;

import java.util.List;

public interface RoleService {
    List<Role> findAll();
}
