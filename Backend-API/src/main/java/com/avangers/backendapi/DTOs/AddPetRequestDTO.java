package com.avangers.backendapi.DTOs;

import jakarta.validation.constraints.NotBlank;

public record AddPetRequestDTO(
                @NotBlank String name,
                @NotBlank String type) {
}
