package com.focuzed.companion.repositories;

import com.focuzed.companion.entities.TemplateExerciseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TemplateExerciseRepository extends JpaRepository<TemplateExerciseEntity, UUID> {
}
