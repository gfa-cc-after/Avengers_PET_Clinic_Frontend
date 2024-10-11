package com.avangers.backendapi.controllers;

import com.avangers.backendapi.services.EmailVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class EmailVerificationController {

    private final EmailVerificationService emailVerificationService;

    @GetMapping("/verify/email")
    public ResponseEntity<String> verifyByEmailLink(@RequestParam String id) {
        try {
            // Use EmailVerificationService to verify the customer by email
            // If successful, return an appropriate response
            emailVerificationService.verifyCustomerByUUID(id);
            return ResponseEntity.status(HttpStatus.OK).body("Email verification successful. Please log in.");
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid verification ID.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID format.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during verification.");
        }
    }
}
