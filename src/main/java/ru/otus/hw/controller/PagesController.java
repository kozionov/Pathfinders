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

    @GetMapping("/user/create")
    public String userCreatePage() {
        return "createUser";
    }

    @GetMapping("/user/add")
    public String userAddPage() {
        return "director/addUser";
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

    @GetMapping("/logRecord/create")
    public String logRecordCreate() {
        return "director/logRecordCreate";
    }
    @GetMapping("/pearls")
    public String pearls() {
        return "director/pearls";
    }

}
