package com.avangers.backendapi.repositories;

import com.avangers.backendapi.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByEmail(String email);

    Optional<Customer> findByEmail(String email);

    void deleteByEmail(String email);
}
