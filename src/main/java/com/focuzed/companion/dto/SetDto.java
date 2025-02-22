package com.focuzed.companion.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record SetDto(
        @NotNull(message = "mandatory field")
        Integer setNumber,
        @NotNull(message = "mandatory field")
        BigDecimal weight,
        @NotNull(message = "mandatory field")
        Integer repetitions) {
}
