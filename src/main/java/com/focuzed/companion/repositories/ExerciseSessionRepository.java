package com.focuzed.companion.repositories;

import com.focuzed.companion.entities.ExerciseSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExerciseSessionRepository extends JpaRepository<ExerciseSessionEntity, UUID> {
}
