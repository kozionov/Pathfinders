package ru.otus.hw.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.entity.Role;
import ru.otus.hw.models.Author;
import ru.otus.hw.security.UserPrincipal;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.RoleService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/api/roles")
    public List<Role> getAllRoles() {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal.getRole().equals("ADMIN")) {
            return roleService.findAll();
        } else {
            return roleService.findAll().stream().filter(x->x.getRoleName().equals("USER")).collect(Collectors.toList());
        }

    }

}
