package com.avangers.backendapi.repositories;

import com.avangers.backendapi.models.Vet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VetRepository extends JpaRepository<Vet, Long> {
    boolean existsByEmail(String email);

    Optional<Vet> findByEmail(String email);

    void deleteByEmail(String email);
}
