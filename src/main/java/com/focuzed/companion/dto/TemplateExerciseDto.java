package com.focuzed.companion.dto;

import java.util.UUID;

public record TemplateExerciseDto(UUID id,
                                  UUID templateId,
                                  UUID exerciseId,
                                  Integer exerciseOrder,
                                  Integer plannedSets,
                                  String notes) {
}
