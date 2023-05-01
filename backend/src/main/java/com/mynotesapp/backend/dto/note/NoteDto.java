package com.mynotesapp.backend.dto.note;

import com.mynotesapp.backend.dto.category.CategoryDto;
import com.mynotesapp.backend.dto.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NoteDto {

    private Long id;

    private String title;

    private String text;

    private Boolean isImportant;

    private Long orderNumber;

    private UserDto owner;

    private LocalDateTime createdDate;

    private CategoryDto category;
}
