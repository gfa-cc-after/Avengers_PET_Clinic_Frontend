package com.avangers.backendapi.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterUserRequestDTO(
// @formatter:off
        @NotBlank(message = "e-mail is required")
        @Email(message = "not valid e-mail")
        String email,
        @NotBlank(message = "password is required")
        @Size(min = 6, message = "password should have at least 6 characters")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z]).*$",
        message = "password should contain at least one uppercase and one lowercase letter")
        String password) {
// @formatter:on
}
