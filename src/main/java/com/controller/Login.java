package com.controller;

import com.models.user.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Login {

    @GetMapping("/login")
    public User login() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
