package com.mynotesapp.backend.util;

import lombok.Getter;

@Getter
public enum RoleEnum {
    USER(1L, "User"),
    ADMIN(2L, "Admin");

    private final Long id;

    private final String name;

    RoleEnum(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
