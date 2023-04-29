package com.mynotesapp.backend.dto.note;

import lombok.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchNoteCriteriaDto {

    private String searchText;

    private Boolean isImportant;

    private List<Long> categories;

    private Pageable pageable;

    private Sort sort;
}
