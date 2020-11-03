package com.thoughtworks.capacity.gtb.mvc.service;

import com.thoughtworks.capacity.gtb.mvc.exception.UsernameExistsException;
import com.thoughtworks.capacity.gtb.mvc.model.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    private final Map<String, User> users = new HashMap<>();

    public void register(User user) {
        if (users.containsKey(user.getUsername())) {
            throw new UsernameExistsException("用户已存在");
        }
        users.put(user.getUsername(), user);
    }
}
