package com.mynotesapp.backend.domain.repository;

import com.mynotesapp.backend.domain.entity.NoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<NoteEntity, Long>, JpaSpecificationExecutor<NoteEntity> {

    Optional<NoteEntity> findByIdAndOwnerId(Long id, Long ownerId);
}
