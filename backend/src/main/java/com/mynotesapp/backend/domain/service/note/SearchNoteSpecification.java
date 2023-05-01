package com.mynotesapp.backend.domain.service.note;

import com.mynotesapp.backend.domain.entity.CategoryEntity;
import com.mynotesapp.backend.domain.entity.NoteEntity;
import com.mynotesapp.backend.dto.note.SearchNoteCriteriaDto;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class SearchNoteSpecification implements Specification<NoteEntity> {

    private final SearchNoteCriteriaDto criteria;

    @Override
    public Predicate toPredicate(Root<NoteEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(
                criteriaBuilder.and(criteriaBuilder.equal(root.join("owner").get("id"), criteria.getOwnerId()))
        );

        if (criteria.getSearchText() != null && !criteria.getSearchText().trim().equals("")) {
            predicates.add(criteriaBuilder.or(
                    getPredicate(criteriaBuilder, root.get("text")),
                    getPredicate(criteriaBuilder, root.get("title"))
            ));
        }

        if (criteria.getIsImportant() != null) {
            predicates.add(
                    criteriaBuilder.and(criteriaBuilder.equal(root.get("isImportant"), criteria.getIsImportant()))
            );
        }

        if (criteria.getCategories() != null && !criteria.getCategories().isEmpty()) {
            Join<NoteEntity, CategoryEntity> join = root.join("category");
            predicates.add(join.get("id").in(criteria.getCategories()));
        }

        query.distinct(true);

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }


    private Predicate getPredicate(CriteriaBuilder criteriaBuilder, Path<String> field) {

        return criteriaBuilder.isTrue(
                criteriaBuilder.function(
                        "REGEXP_LIKE",
                        Boolean.class,
                        criteriaBuilder.lower(field),
                        criteriaBuilder.literal(Pattern.quote(criteria.getSearchText().toLowerCase()))
                )
        );
    }
}
