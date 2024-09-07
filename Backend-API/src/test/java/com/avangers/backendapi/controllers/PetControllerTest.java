package com.avangers.backendapi.controllers;

import com.avangers.backendapi.DTOs.AddPetRequestDTO;
import com.avangers.backendapi.DTOs.AddPetResponseDTO;
import com.avangers.backendapi.DTOs.FindUserResponseDTO;
import com.avangers.backendapi.models.Pet;
import com.avangers.backendapi.services.PetService;
import com.avangers.backendapi.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
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
@WithMockUser(username = "user@example.com")  // Adds a mock user automatically
public class PetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PetService petService;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetMyPets() throws Exception {
        // Mocking data
        FindUserResponseDTO userDTO = new FindUserResponseDTO(1, "user@example.com");
        when(userService.findUserByEmail(anyString())).thenReturn(userDTO);

        Pet mockPet = new Pet();
        mockPet.setName("hauko");
        mockPet.setType("hauky");
        List<Pet> pets = Collections.singletonList(mockPet);
        when(petService.getPetsByOwnerId(1L)).thenReturn(pets);

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
        AddPetResponseDTO responseDTO = new AddPetResponseDTO();

        when(petService.addPet(any(AddPetRequestDTO.class), eq(email))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/pets/add")
//                        .principal(() -> email) // Simulating the Principal with email
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseDTO)));

        verify(petService, times(1)).addPet(any(AddPetRequestDTO.class), eq(email));
    }
}
