package com.focuzed.companion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ExerciseDto(
        @NotBlank(message = "mandatory field")
        @Size(min = 2, max = 255, message = "filed doesn't have the recomended size")
        String title,
        @NotBlank(message = "mandatory field")
        @Size(min = 2, max = 255, message = "filed doesn't have the recomended size")
        String description,
        @NotBlank(message = "mandatory field")
        String url) {
}
