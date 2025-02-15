package com.focuzed.companion.repositories;

import com.focuzed.companion.entities.ExerciseSet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExerciseSetRepository extends JpaRepository<ExerciseSet, UUID> {

}
