package com.thoughtworks.capacity.gtb.mvc.controller;

import com.thoughtworks.capacity.gtb.mvc.controller.dto.Error;
import com.thoughtworks.capacity.gtb.mvc.exception.PasswordMismatchException;
import com.thoughtworks.capacity.gtb.mvc.exception.UserNotFoundException;
import com.thoughtworks.capacity.gtb.mvc.exception.UsernameExistsException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    @ExceptionHandler(UsernameExistsException.class)
    public ResponseEntity<Error> handleUserExist(UsernameExistsException exception) {
        return ResponseEntity.badRequest()
                .body(new Error(HttpStatus.BAD_REQUEST.value(), exception.getMessage()));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public ResponseEntity<Error> handleInvalidField(Exception exception) {
        Stream<String> errorMessages;
        if (exception instanceof MethodArgumentNotValidException) {
            errorMessages = ((MethodArgumentNotValidException) exception).getBindingResult().getFieldErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage);
        } else if (exception instanceof ConstraintViolationException) {
            errorMessages = ((ConstraintViolationException) exception).getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage);
        } else {
            errorMessages = Stream.empty(); // defensive
        }
        return ResponseEntity.badRequest()
                .body(new Error(HttpStatus.BAD_REQUEST.value(), errorMessages.collect(Collectors.joining("|"))));
    }

    @ExceptionHandler({UserNotFoundException.class, PasswordMismatchException.class})
    public ResponseEntity<Error> handleInvalidLogin() {
        return new ResponseEntity<>(new Error(HttpStatus.UNAUTHORIZED.value(), "用户名或密码错误"), HttpStatus.UNAUTHORIZED);
    }
}
