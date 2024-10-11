package com.avangers.backendapi.controllers;

import com.avangers.backendapi.DTOs.*;
import com.avangers.backendapi.services.VetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vets/clinics")
@RequiredArgsConstructor
public class ClinicController {

    private final VetService vetService;

    @PostMapping
    public ClinicResponseDTO addClinic(@RequestParam String vetEmail, @RequestBody ClinicRequestDTO clinicRequestDTO) {
        return vetService.addClinic(vetEmail, clinicRequestDTO);
    }

    @PutMapping("/{clinicId}")
    public ClinicResponseDTO updateClinic(@PathVariable Long clinicId, @RequestBody ClinicRequestDTO clinicRequestDTO) {
        return vetService.updateClinic(clinicId, clinicRequestDTO);
    }

    @DeleteMapping("/{clinicId}")
    public void deleteClinic(@PathVariable Long clinicId) {
        vetService.deleteClinic(clinicId);
    }

    @GetMapping
    public List<ClinicResponseDTO> getClinics(@RequestParam String vetEmail) {
        return vetService.findAllClinicsByVet(vetEmail);
    }
}
