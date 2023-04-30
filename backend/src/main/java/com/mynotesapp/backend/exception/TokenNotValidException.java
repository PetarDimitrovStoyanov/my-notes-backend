package com.mynotesapp.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class TokenNotValidException extends RuntimeException {

    public TokenNotValidException(String token) {
        super(String.format("Token: %s is invalid.", token));
    }
}
