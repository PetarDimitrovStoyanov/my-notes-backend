package com.mynotesapp.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityByNameNotFoundException extends RuntimeException {

    public EntityByNameNotFoundException(String clazz, String name) {
        super(String.format("%s with name: %s was not found.", clazz, name));
    }
}
