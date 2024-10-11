package com.avangers.backendapi.services;

import com.avangers.backendapi.DTOs.*;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface VetService extends UserDetailsService {

    // Vet method
    RegisterUserResponseDTO addVet(RegisterUserRequestDTO registerUserRequestDTO);

    DeleteUserResponseDTO deleteVet(String email);

    UpdateUserResponseDTO updateVet(String email, UpdateUserRequestDTO updateUserRequestDTO);

    LoginUserResponseDTO loginVet(LoginUserRequestDTO loginUserRequestDTO);

    FindUserResponseDTO findVetByEmail(String email);

    // Vet methods with clinic
    ClinicResponseDTO addClinic(String vetEmail, ClinicRequestDTO clinicRequestDTO);

    ClinicResponseDTO updateClinic(Long clinicId, ClinicRequestDTO clinicRequestDTO);

    DeleteClinicResponseDTO deleteClinic(Long clinicId);

    List<ClinicResponseDTO> findAllClinicsByVet(String vetEmail);

    ClinicResponseDTO findClinicById(Long clinicId);
}
