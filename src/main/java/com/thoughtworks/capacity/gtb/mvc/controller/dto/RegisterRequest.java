package com.thoughtworks.capacity.gtb.mvc.controller.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class RegisterRequest {
    @NotBlank(message = "用户名不为空")
    @Pattern(message = "用户名不合法", regexp = "^[\\w]{3,10}$")
    private String username;
    @NotBlank(message = "密码不为空")
    @Pattern(message = "密码不合法", regexp = "^.{5,12}$")
    private String password;
    @Email(message = "邮箱地址不合法")
    private String email;

    public RegisterRequest(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
