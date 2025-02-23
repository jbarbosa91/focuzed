package com.focuzed.companion.repositories;

import com.focuzed.companion.entities.PlanDayTemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PlanDayTemplateRepository extends JpaRepository<PlanDayTemplateEntity, UUID> {
}
