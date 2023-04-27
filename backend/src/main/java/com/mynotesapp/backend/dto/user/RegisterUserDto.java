package com.mynotesapp.backend.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserDto {

    @NotBlank(message = "The field 'email' cannot be null or empty.")
    @Size(max = 255, message = "The field 'email' cannot be more than 255 symbols")
    private String email;

    @NotBlank(message = "The field 'name' cannot be null or empty.")
    @Size(max = 255, message = "The field 'name' cannot be more than 255 symbols")
    private String name;

    @NotBlank(message = "The field 'password' cannot be null or empty.")
    @Size(max = 100, message = "The field 'password' cannot be more than 100 symbols")
    private String password;
}
