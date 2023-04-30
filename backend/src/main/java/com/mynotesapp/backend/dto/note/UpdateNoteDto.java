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
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateNoteDto {

    @NotBlank(message = "The field 'id' cannot be null")
    private Long id;

    @Size(max = 5000, message = "The field 'title' cannot be more than 5000 symbol")
    @NotBlank(message = "The field 'title' cannot be null or empty")
    private String title;

    @Size(max = 5000, message = "The field 'text' cannot be more than 5000 symbol")
    @NotBlank(message = "The field 'text' cannot be null or empty")
    private String text;

    @NotNull(message = "The field 'isImportant' cannot be null")
    private Boolean isImportant;

    @NotNull(message = "The field 'owner' cannot be null")
    private @Valid UserDto owner;

    @NotNull(message = "The field 'category' cannot be null")
    private @Valid CategoryDto category;

    @NotNull(message = "The field 'orderDateTime' cannot be null")
    private LocalDateTime orderDateTime;
}
