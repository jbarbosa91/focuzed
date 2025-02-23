package com.focuzed.companion.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record TrainingPlanDto(UUID id,
                              UUID userId,
                              List<PlanDayTemplateDto> planDayTemplateDtos,
                              String name,
                              LocalDate endDate,
                              Integer daysPerWeek,
                              Boolean isActive) {
}
