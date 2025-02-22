package com.focuzed.companion.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record SetDto(
        @NotNull(message = "mandatory field")
        Integer setNumber,
        @NotNull(message = "mandatory field")
        @Min(value = 0, message = "minimum is 0")
        BigDecimal weight,
        @NotNull(message = "mandatory field")
        @Min(value = 0, message = "minimum is 0")
        Integer repetitions) {
}
