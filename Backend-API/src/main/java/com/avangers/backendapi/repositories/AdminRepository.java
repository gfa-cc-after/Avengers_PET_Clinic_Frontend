package com.avangers.backendapi.repositories;

import com.avangers.backendapi.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    boolean existsByEmail(String email);

    Optional<Admin> findByEmail(String email);

    void deleteByEmail(String email);
}
