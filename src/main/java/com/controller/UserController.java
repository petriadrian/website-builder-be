package com.controller;

import com.models.User;
import com.service.SiteService;
import com.service.UserService;
import com.utils.Utils;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    UserService userService;
    SiteService siteService;

    @GetMapping("/login")
    public User get(@RequestParam(value = "origin") String origin) {
        return Utils.getLoggedInUser();
    }
}
