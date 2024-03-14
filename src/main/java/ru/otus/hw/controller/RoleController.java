package ru.otus.hw.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.entity.Role;
import ru.otus.hw.models.Author;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.RoleService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/api/roles")
    public List<Role> getAllRoles() {
        return roleService.findAll();
    }

}
