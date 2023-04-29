package com.mynotesapp.backend.dto.user;

import com.mynotesapp.backend.dto.role.RoleDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotBlank(message = "The field 'id' cannot be null")
    private Long id;

    private String email;

    private String fullName;

    private List<RoleDto> roles;
}
