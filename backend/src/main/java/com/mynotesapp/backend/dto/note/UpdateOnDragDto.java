package com.mynotesapp.backend.dto.note;

import com.mynotesapp.backend.dto.category.CategoryDto;
import com.mynotesapp.backend.dto.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateOnDragDto {

    @NotBlank(message = "The field 'id' cannot be null")
    private Long id;

    @NotNull(message = "The field 'orderDateTime' cannot be null")
    private LocalDateTime orderDateTime;

    @NotNull(message = "The field 'category' cannot be null")
    private @Valid CategoryDto category;

    @NotNull(message = "The field 'owner' cannot be null")
    private @Valid UserDto owner;

    @NotNull(message = "The field 'isIncrement' cannot be null")
    private Boolean isIncrement;
}
