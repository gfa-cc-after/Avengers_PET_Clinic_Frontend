package com.avangers.backendapi.controllers;

import com.avangers.backendapi.DTOs.AddPetRequestDTO;
import com.avangers.backendapi.DTOs.AddPetResponseDTO;
import com.avangers.backendapi.DTOs.FindUserResponseDTO;
import com.avangers.backendapi.services.CustomerService;
import com.avangers.backendapi.services.PetService;
import com.avangers.backendapi.DTOs.PetDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "user@example.com") // Adds a mock user automatically
public class PetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PetService petService;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Should return list of pets owned by the user")
    @Test
    public void testGetMyPets() throws Exception {
        // Mocking data using DTO
        FindUserResponseDTO userDTO = new FindUserResponseDTO(1L, "user@example.com");
        when(customerService.findCustomerByEmail(anyString())).thenReturn(userDTO);

        // Using PetDTO for mock return value
        PetDTO mockPetDTO = new PetDTO(
                1L,
                "hauko",
                "hauky");

        List<PetDTO> petsDTOs = Collections.singletonList(mockPetDTO);
        when(petService.getPetsByOwnerId(1L)).thenReturn(petsDTOs);

        // Performing the test
        mockMvc.perform(get("/api/pets/my-pets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("hauko"))
                .andExpect(jsonPath("$[0].type").value("hauky"));
    }

    @Test
    void testAddPetSuccessfully() throws Exception {
        String email = "user@example.com";
        AddPetRequestDTO requestDTO = new AddPetRequestDTO("Lucky", "Dog");
        AddPetResponseDTO responseDTO = new AddPetResponseDTO(1L);

        when(petService.addPet(any(AddPetRequestDTO.class), eq(email))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/pets/add")
                // .principal(() -> email) // Simulating the Principal with email
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseDTO)));

        verify(petService, times(1)).addPet(any(AddPetRequestDTO.class), eq(email));
    }
}
