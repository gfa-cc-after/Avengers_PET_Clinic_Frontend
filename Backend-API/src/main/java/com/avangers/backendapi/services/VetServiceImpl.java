package com.avangers.backendapi.services;

import com.avangers.backendapi.DTOs.*;
import com.avangers.backendapi.models.Clinic;
import com.avangers.backendapi.models.Vet;
import com.avangers.backendapi.repositories.ClinicRepository;
import com.avangers.backendapi.repositories.VetRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VetServiceImpl implements VetService {

    private final VetRepository vetRepository;
    private final ClinicRepository clinicRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return vetRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email is not in database"));

    }

    @Override
    public RegisterUserResponseDTO addVet(RegisterUserRequestDTO registerUserRequestDTO) {
        if (vetRepository.existsByEmail(registerUserRequestDTO.email())) {
            throw new IllegalArgumentException("Email is already in use");
        }

        Vet newVet = Vet.builder()
                .email(registerUserRequestDTO.email())
                .password(passwordEncoder.encode(registerUserRequestDTO.password()))
                .build();
        vetRepository.save(newVet);
        return new RegisterUserResponseDTO(newVet.getId(), newVet.getEmail(), false);
    }

    @Override
    public DeleteUserResponseDTO deleteVet(String email) {
        vetRepository.deleteByEmail(email);
        return new DeleteUserResponseDTO("User was successfully deleted");
    }

    @Override
    public UpdateUserResponseDTO updateVet(String email, UpdateUserRequestDTO updateUserRequestDTO) {
        Vet existingVet = vetRepository.findByEmail(email).orElse(null);
        // check if User exists
        if (existingVet == null) {
            throw new UsernameNotFoundException("User not found");
        }
        // check if new email is not already used by another user
        if (!existingVet.getEmail().equals(updateUserRequestDTO.email())
                && vetRepository.existsByEmail(updateUserRequestDTO.email())) {
            throw new IllegalArgumentException("The email already exist");
        }

        existingVet.setEmail(updateUserRequestDTO.email());
        existingVet.setPassword(passwordEncoder.encode(updateUserRequestDTO.password()));
        vetRepository.save(existingVet);

        return new UpdateUserResponseDTO(updateUserRequestDTO.email(), "Update was successful");
    }

    @Override
    public LoginUserResponseDTO loginVet(LoginUserRequestDTO loginUserRequestDTO) {
        Vet vet = vetRepository.findByEmail(loginUserRequestDTO.email())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (!passwordEncoder.matches(loginUserRequestDTO.password(), vet.getPassword())) {
            throw new RuntimeException("Password is not valid");
        }
        return new LoginUserResponseDTO(jwtTokenService.generateToken(vet));
    }

    @Override
    public FindUserResponseDTO findVetByEmail(String email) {
        Vet vet = vetRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new FindUserResponseDTO(vet.getId(), vet.getEmail());
    }

    //

    @Override
    @Transactional
    public ClinicResponseDTO addClinic(String vetEmail, ClinicRequestDTO clinicRequestDTO) {
        Vet vet = vetRepository.findByEmail(vetEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Vet not found"));

        Clinic clinic = Clinic.builder()
                .name(clinicRequestDTO.name())
                .street(clinicRequestDTO.street())
                .city(clinicRequestDTO.city())
                .zipcode(clinicRequestDTO.zipcode())
                .longitude(clinicRequestDTO.longitude())
                .latitude(clinicRequestDTO.latitude())
                .description(clinicRequestDTO.description())
                .vet(vet)
                .build();

        clinicRepository.save(clinic);
        return new ClinicResponseDTO(clinic.getId(), clinic.getName(), clinic.getStreet(), clinic.getCity(),
                clinic.getZipcode(),
                clinic.getLongitude(), clinic.getLatitude(), clinic.getDescription());
    }

    @Override
    @Transactional
    public ClinicResponseDTO updateClinic(Long clinicId, ClinicRequestDTO clinicRequestDTO) {
        Clinic clinic = clinicRepository.findById(clinicId)
                .orElseThrow(() -> new IllegalArgumentException("Clinic not found"));

        clinic.setName(clinicRequestDTO.name());
        clinic.setStreet(clinicRequestDTO.street());
        clinic.setCity(clinicRequestDTO.city());
        clinic.setZipcode(clinicRequestDTO.zipcode());
        clinic.setLongitude(clinicRequestDTO.longitude());
        clinic.setLatitude(clinicRequestDTO.latitude());
        clinic.setDescription(clinicRequestDTO.description());
        clinicRepository.save(clinic);

        return new ClinicResponseDTO(clinic.getId(), clinic.getName(), clinic.getStreet(), clinic.getCity(),
                clinic.getZipcode(),
                clinic.getLongitude(), clinic.getLatitude(), clinic.getDescription());
    }

    @Override
    public DeleteClinicResponseDTO deleteClinic(Long clinicId) {
        Clinic clinic = clinicRepository.findById(clinicId)
                .orElseThrow(() -> new RuntimeException("Clinic not found"));

        clinicRepository.delete(clinic);

        return new DeleteClinicResponseDTO("Clinic was successfully deleted.");
    }

    @Override
    public List<ClinicResponseDTO> findAllClinicsByVet(String vetEmail) {
        Vet vet = vetRepository.findByEmail(vetEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Vet not found"));

        List<Clinic> clinics = clinicRepository.findAllByVet(vet);

        return clinics.stream()
                .map(clinic -> new ClinicResponseDTO(
                        clinic.getId(),
                        clinic.getName(),
                        clinic.getStreet(),
                        clinic.getCity(),
                        clinic.getZipcode(),
                        clinic.getLongitude(),
                        clinic.getLatitude(),
                        clinic.getDescription()))
                .collect(Collectors.toList());
    }

    @Override
    public ClinicResponseDTO findClinicById(Long clinicId) {
        Clinic clinic = clinicRepository.findById(clinicId)
                .orElseThrow(() -> new RuntimeException("Clinic not found"));

        return new ClinicResponseDTO(
                clinic.getId(),
                clinic.getName(),
                clinic.getStreet(),
                clinic.getCity(),
                clinic.getZipcode(),
                clinic.getLongitude(),
                clinic.getLatitude(),
                clinic.getDescription());
    }
}
