package com.mynotesapp.backend.dto.user;

import jakarta.persistence.Column;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotBlank(message = "The field 'id' cannot be null or empty")
    private String id;

    private String email;

    private String name;
}
