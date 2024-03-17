package ru.otus.hw.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.hw.security.UserPrincipal;

@Controller
public class PagesController {

    @GetMapping({"/"})
    public String mainPage() {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal.getRole().equals("ADMIN")) {
            return "admin/main";
        } else {
            return "director/main";
        }
    }


    @GetMapping("/book")
    public String bookDetailsPage() {
        return "details";
    }

    @GetMapping("/book/create")
    public String bookCreatePage() {
        return "create";
    }

    @GetMapping("/user/create")
    public String userCreatePage() {
        return "createUser";
    }

    @GetMapping("/user/edit")
    public String userEditPage() {
        return "editUser";
    }

    @GetMapping("/club/create")
    public String clubCreatePage() {
        return "createClub";
    }

    @GetMapping("/club/edit")
    public String clubEditPage() {
        return "editClub";
    }

    @GetMapping("/book/edit")
    public String bookEditPage() {
        return "edit";
    }
}
