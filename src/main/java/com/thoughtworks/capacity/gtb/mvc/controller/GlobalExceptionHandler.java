package com.thoughtworks.capacity.gtb.mvc.controller;

import com.thoughtworks.capacity.gtb.mvc.controller.dto.Error;
import com.thoughtworks.capacity.gtb.mvc.exception.PasswordMismatchException;
import com.thoughtworks.capacity.gtb.mvc.exception.UserNotFoundException;
import com.thoughtworks.capacity.gtb.mvc.exception.UsernameExistsException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UsernameExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Error handleUserExist(UsernameExistsException exception) {
        return new Error(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Error handleInvalidRegisterField(MethodArgumentNotValidException exception) {
        String errorMessages = exception.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("|"));
        return new Error(HttpStatus.BAD_REQUEST.value(), errorMessages);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Error handleInvalidLoginField(ConstraintViolationException exception) {
        String errorMessages = exception.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("|"));
        return new Error(HttpStatus.BAD_REQUEST.value(), errorMessages);
    }

    @ExceptionHandler({UserNotFoundException.class, PasswordMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Error handleInvalidLogin(Exception exception) {
        return new Error(HttpStatus.BAD_REQUEST.value(), "用户名或密码错误");
    }
}
