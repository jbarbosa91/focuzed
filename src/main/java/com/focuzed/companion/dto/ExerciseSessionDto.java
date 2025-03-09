package com.focuzed.companion.dto;

import java.util.List;
import java.util.UUID;

public record ExerciseSessionDto(UUID exerciseId,
                                 List<SetDto> sets,
                                 Integer exerciseOrder) {
}
