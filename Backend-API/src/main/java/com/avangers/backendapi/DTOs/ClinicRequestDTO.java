package com.avangers.backendapi.DTOs;

public record ClinicRequestDTO(
                String name,
                String street,
                String city,
                String zipcode,
                double longitude,
                double latitude,
                String description) {
}