package com.thoughtworks.capacity.gtb.mvc.service;

import com.thoughtworks.capacity.gtb.mvc.controller.dto.RegisterRequest;
import com.thoughtworks.capacity.gtb.mvc.exception.PasswordMismatchException;
import com.thoughtworks.capacity.gtb.mvc.exception.UserNotFoundException;
import com.thoughtworks.capacity.gtb.mvc.exception.UsernameExistsException;
import com.thoughtworks.capacity.gtb.mvc.model.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    private final Map<String, User> users = new HashMap<>();

    public void register(RegisterRequest registerRequest) {
        if (users.containsKey(registerRequest.getUsername())) {
            throw new UsernameExistsException("用户已存在");
        }
        User newUser = new User(users.size() + 1,
                registerRequest.getUsername(),
                registerRequest.getPassword(),
                registerRequest.getEmail());
        users.put(newUser.getUsername(), newUser);
    }

    public User login(String username, String password) {
        if (!users.containsKey(username)) {
            throw new UserNotFoundException();
        }
        User user = users.get(username);
        if (!user.getPassword().equals(password)) {
            throw new PasswordMismatchException();
        }
        return user;
    }
}
