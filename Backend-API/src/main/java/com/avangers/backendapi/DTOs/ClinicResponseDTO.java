package com.avangers.backendapi.DTOs;

public record ClinicResponseDTO(
                Long id,
                String name,
                String street,
                String city,
                String zipcode,
                double longitude,
                double latitude,
                String description) {
}