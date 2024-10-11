package com.avangers.backendapi.controllers;

import com.avangers.backendapi.DTOs.*;
import com.avangers.backendapi.event.UserRegistrationEvent;
import com.avangers.backendapi.services.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("http://localhost:5173/")
@RequiredArgsConstructor
public class UserController {

    private final ApplicationEventPublisher eventPublisher;
    private final CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterUserRequestDTO registerUserRequestDTO) {
        try {
            // Call the customer service to add the customer
            RegisterUserResponseDTO response = customerService.addCustomer(registerUserRequestDTO);
            // Publish the user registration event with RegisterUserResponseDTO
            eventPublisher.publishEvent(new UserRegistrationEvent(response));
            // Return the response DTO
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/api/users")
    public ResponseEntity<?> updateUser(Principal principal,
            @Valid @RequestBody UpdateUserRequestDTO updateUserRequestDTO) {
        try {
            UpdateUserResponseDTO response = customerService.updateCustomer(principal.getName(), updateUserRequestDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            HashMap<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginUserRequestDTO loginUserRequestDTO) {
        try {
            return ResponseEntity.ok(customerService.loginCustomer(loginUserRequestDTO));
        } catch (IllegalArgumentException e) {
            HashMap<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<DeleteUserResponseDTO> deleteUser(Principal principal) {
        return new ResponseEntity<>(customerService.deleteCustomer(principal.getName()), HttpStatus.OK);
    }
}
