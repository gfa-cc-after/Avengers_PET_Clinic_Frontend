package com.avangers.backendapi.services;

import com.avangers.backendapi.models.Customer;
import com.avangers.backendapi.models.EmailVerification;
import com.avangers.backendapi.repositories.CustomerRepository;
import com.avangers.backendapi.repositories.EmailVerificationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailVerificationService {

    private final EmailVerificationRepository emailVerificationRepository;
    private final CustomerRepository customerRepository;

    @Transactional
    // Verifies the customer by email
    public void verifyCustomerByUUID(String id) throws UsernameNotFoundException {
        String emailOfVerifiablePerson = emailVerificationRepository.findByVerificationId(id)
                .orElseThrow(() -> new UsernameNotFoundException("Email verification not found"))
                .getEmail();
        Customer customer = customerRepository.findByEmail(emailOfVerifiablePerson)
                .orElseThrow(() -> new UsernameNotFoundException("Customer not found"));

        customer.setVerified(true);  // Mark the customer as verified
        customerRepository.save(customer);  // Save the updated customer
        emailVerificationRepository.deleteEmailVerificationByVerificationId(id);  // Delete the email verification
    }

    public String generateVerification(String email) {
        EmailVerification emailVerification = new EmailVerification();
        emailVerification.setEmail(email);
        emailVerification.setVerificationId(UUID.randomUUID().toString());
        emailVerificationRepository.save(emailVerification);
        return emailVerification.getVerificationId();
    }
}
