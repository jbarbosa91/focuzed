package com.focuzed.companion.dto;

import com.focuzed.companion.entities.Status;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record SessionDto(List<ExerciseSessionDto> exercises,
                         @NotNull
                         @Min(value = 1, message = "minimum value is 1")
                         @Max(value = 7, message = "maximum value is 7")
                         Integer day,
                         Status status) {
}
