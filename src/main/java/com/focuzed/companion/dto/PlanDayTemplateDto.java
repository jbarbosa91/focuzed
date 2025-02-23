package com.focuzed.companion.dto;

import java.util.List;
import java.util.UUID;

public record PlanDayTemplateDto(UUID id,
                                 UUID planId,
                                 List<TemplateExerciseDto> templateExerciseDtos,
                                 Integer day,
                                 String description) {
}
