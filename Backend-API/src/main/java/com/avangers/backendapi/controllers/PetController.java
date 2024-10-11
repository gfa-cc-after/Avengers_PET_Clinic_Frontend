package com.avangers.backendapi.controllers;

import com.avangers.backendapi.DTOs.AddPetRequestDTO;
import com.avangers.backendapi.DTOs.AddPetResponseDTO;
import com.avangers.backendapi.DTOs.FindUserResponseDTO;
import com.avangers.backendapi.services.CustomerService;
import com.avangers.backendapi.services.PetService;
import com.avangers.backendapi.DTOs.PetDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;
    private final CustomerService customerService;

    @GetMapping("/my-pets")
    public ResponseEntity<?> getMyPets(Principal principal) {
        try {
            FindUserResponseDTO user = customerService.findCustomerByEmail(principal.getName());
            List<PetDTO> pets = petService.getPetsByOwnerId(Long.valueOf(user.id()));
            return new ResponseEntity<>(pets, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            HashMap<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addPet(Principal principal, @Valid @RequestBody AddPetRequestDTO addPetRequestDTO) {
        try {
            AddPetResponseDTO responseAddPet = petService.addPet(addPetRequestDTO, principal.getName());
            return new ResponseEntity<>(responseAddPet, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            HashMap<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

}
