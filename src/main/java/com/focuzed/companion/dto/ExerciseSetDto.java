package com.focuzed.companion.dto;

import com.focuzed.companion.entities.Difficulty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ExerciseSetDto(
        @NotNull(message = "mandatory field")
        BigDecimal weight,
        @NotNull(message = "mandatory field")
        BigDecimal reps,
        Difficulty difficulty) {
}
