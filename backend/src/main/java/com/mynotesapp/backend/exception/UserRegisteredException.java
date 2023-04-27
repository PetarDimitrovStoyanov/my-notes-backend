package com.mynotesapp.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserRegisteredException extends RuntimeException {

    public UserRegisteredException(String email) {
        super(String.format("The user with email: %s is already registered", email));
    }
}
