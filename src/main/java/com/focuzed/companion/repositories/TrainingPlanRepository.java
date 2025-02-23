package com.focuzed.companion.repositories;

import com.focuzed.companion.entities.TrainingPlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TrainingPlanRepository extends JpaRepository<TrainingPlanEntity, UUID> {
}
