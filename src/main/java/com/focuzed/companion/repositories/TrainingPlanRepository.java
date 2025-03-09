package com.focuzed.companion.repositories;

import com.focuzed.companion.entities.TrainingPlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TrainingPlanRepository extends JpaRepository<TrainingPlanEntity, UUID> {
    List<TrainingPlanEntity> findTrainingPlanEntitiesByUserEntityId(UUID userId);
}
