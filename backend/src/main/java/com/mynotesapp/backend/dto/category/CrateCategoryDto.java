package com.mynotesapp.backend.dto.category;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CrateCategoryDto {

    @NotBlank(message = "The field 'name' cannot be null or empty.")
    @Size(max = 255, message = "The size cannot be more than 255 symbols")
    private String name;
}
