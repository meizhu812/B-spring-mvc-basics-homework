package com.thoughtworks.capacity.gtb.mvc.controller;

import com.thoughtworks.capacity.gtb.mvc.controller.dto.RegisterRequest;
import com.thoughtworks.capacity.gtb.mvc.model.User;
import com.thoughtworks.capacity.gtb.mvc.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.net.URI;

@RestController
@Validated
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterRequest registerRequest) {
        userService.register(registerRequest);
        return ResponseEntity.created(URI.create("UNKNOWN"))
                .build();  //TODO 需要其他endpoint直达用户信息
    }

    @GetMapping("/login")
    public ResponseEntity<User> login(@RequestParam @Pattern(message = "用户名不合法", regexp = "^[\\w]{3,10}$") String username,
                                      @RequestParam @Pattern(message = "密码不合法", regexp = "^.{5,12}$") String password) {
        return ResponseEntity.ok()
                .body(userService.login(username, password));
    }
}
