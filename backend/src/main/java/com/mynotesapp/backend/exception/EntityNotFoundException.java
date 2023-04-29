package com.mynotesapp.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(Long id, String clazz) {
        super(String.format("%s with id: %d was not found.", clazz, id));
    }
}
