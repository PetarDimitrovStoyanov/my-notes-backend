package com.mynotesapp.backend.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "notes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoteEntity extends BaseEntity {

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String title;

    @Column(columnDefinition = "VARCHAR(1000)", nullable = false)
    private String text;

    @Column(name = "is_important", nullable = false, columnDefinition = "boolean default false")
    private Boolean isImportant;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    private Long orderNumber;

    @ManyToOne
    @JoinColumn(name="category_id", nullable=false)
    private CategoryEntity category;

    @ManyToOne
    @JoinColumn(name="owner_id", nullable=false)
    private UserEntity owner;
}
