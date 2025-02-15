package com.focuzed.companion.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserDto(
        @Email(message = "invalid")
        @NotBlank(message = "mandatory field")
        String email,
        @NotBlank(message = "mandatory field")
        String name,
        @NotBlank(message = "mandatory field")
        String password,
        @NotBlank(message = "mandatory field")
        String role) {
}
