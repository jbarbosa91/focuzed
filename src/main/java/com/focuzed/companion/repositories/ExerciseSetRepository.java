package com.focuzed.companion.repositories;

import com.focuzed.companion.entities.SetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExerciseSetRepository extends JpaRepository<SetEntity, UUID> {

}
