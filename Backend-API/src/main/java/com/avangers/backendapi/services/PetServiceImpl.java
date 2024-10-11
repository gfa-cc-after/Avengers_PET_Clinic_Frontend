package com.avangers.backendapi.services;

import com.avangers.backendapi.DTOs.AddPetRequestDTO;
import com.avangers.backendapi.DTOs.AddPetResponseDTO;
import com.avangers.backendapi.DTOs.PetDTO;
import com.avangers.backendapi.models.Customer;
import com.avangers.backendapi.models.Pet;
import com.avangers.backendapi.repositories.CustomerRepository;
import com.avangers.backendapi.repositories.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final CustomerRepository customerRepository;

    @Override
    public List<PetDTO> getPetsByOwnerId(Long ownerId) {
        List<Pet> pets = petRepository.findByOwnerId(ownerId);
        return pets.stream()
                .map(pet -> new PetDTO(pet.getId(), pet.getName(), pet.getType()))
                .collect(Collectors.toList());
    }

    @Override
    public AddPetResponseDTO addPet(AddPetRequestDTO addPetRequestDTO, String email) {

        // check if the user exists
        Customer petOwner = customerRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User not found"));

        Pet newPet = new Pet();
        newPet.setName(addPetRequestDTO.name());
        newPet.setType(addPetRequestDTO.type());
        newPet.setOwner(petOwner);

        Long id = petRepository.save(newPet).getId();

        return new AddPetResponseDTO(id);
    }
}
