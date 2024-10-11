package com.avangers.backendapi.repositories;

import com.avangers.backendapi.models.Clinic;
import com.avangers.backendapi.models.Vet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClinicRepository extends JpaRepository<Clinic, Long> {
    // Find all clinics associated with a specific vet
    List<Clinic> findAllByVet(Vet vet);
}