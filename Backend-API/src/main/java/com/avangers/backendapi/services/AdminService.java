package com.avangers.backendapi.services;

import com.avangers.backendapi.DTOs.*;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AdminService extends UserDetailsService {
    RegisterUserResponseDTO addAdmin(RegisterUserRequestDTO registerUserRequestDTO);

    DeleteUserResponseDTO deleteAdmin(String email);

    UpdateUserResponseDTO updateAdmin(String email, UpdateUserRequestDTO updateUserRequestDTO);

    LoginUserResponseDTO loginAdmin(LoginUserRequestDTO loginUserRequestDTO);

    FindUserResponseDTO findAdminByEmail(String email);
}

