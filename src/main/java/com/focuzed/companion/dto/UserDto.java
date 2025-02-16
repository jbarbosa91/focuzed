package com.focuzed.companion.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record UserDto(
        UUID id,
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
