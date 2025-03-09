package com.focuzed.companion.dto;

import java.time.LocalDate;
import java.util.List;

public record TrainingPlanDto(List<PlanDayTemplateDto> planDayTemplateDtos,
                              LocalDate endDate,
                              Integer daysPerWeek,
                              Boolean isActive) {
}
