package com.thoughtworks.capacity.gtb.mvc.controller;

import com.thoughtworks.capacity.gtb.mvc.model.User;
import com.thoughtworks.capacity.gtb.mvc.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody @Valid User user) {
        userService.register(user);
    }

    @GetMapping("/login")
    @Validated
    @ResponseStatus(HttpStatus.OK)
    public User login(@RequestParam @Pattern(message = "用户名不合法", regexp = "^[\\w]{3,10}$") String username,
                      @RequestParam @Pattern(message = "密码不合法", regexp = "^.{5,12}$") String password) {
        return userService.login(username, password);
    }
}
