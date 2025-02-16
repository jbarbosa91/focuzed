package com.focuzed.companion.dto;

import com.focuzed.companion.entities.Difficulty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record SetDto(
        @NotNull(message = "mandatory field")
        Integer setNumber,
        BigDecimal weight,
        @NotNull(message = "mandatory field")
        BigDecimal reps,
        Difficulty difficulty) {
}
