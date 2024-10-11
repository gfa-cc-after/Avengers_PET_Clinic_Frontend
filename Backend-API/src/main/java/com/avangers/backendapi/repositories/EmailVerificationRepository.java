package com.avangers.backendapi.repositories;

import com.avangers.backendapi.models.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmailVerificationRepository extends JpaRepository<EmailVerification, UUID> {

  Optional<EmailVerification> findByVerificationId(String verificationId);
  void deleteEmailVerificationByVerificationId(String verificationId);
}
