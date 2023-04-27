package com.mynotesapp.backend.domain.repository;

import com.mynotesapp.backend.domain.entity.NoteEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NoteRepository extends JpaRepository<NoteEntity, UUID>, JpaSpecificationExecutor<NoteEntity> {
    List<NoteEntity> findAllByOwnerId(UUID id);

    Optional<NoteEntity> findByIdAndOwnerId(UUID id, UUID ownerId);
}
