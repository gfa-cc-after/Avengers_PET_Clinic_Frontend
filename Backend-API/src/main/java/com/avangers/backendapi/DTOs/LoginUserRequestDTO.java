package com.avangers.backendapi.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginUserRequestDTO (
        @Email String email,
        @NotBlank String password) {
}
