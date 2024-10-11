package com.avangers.backendapi.services;

import com.avangers.backendapi.DTOs.*;
import com.avangers.backendapi.models.Admin;
import com.avangers.backendapi.models.User;
import com.avangers.backendapi.repositories.AdminRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return adminRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email is not in database"));
    }

    @Override
    public RegisterUserResponseDTO addAdmin(RegisterUserRequestDTO registerUserRequestDTO) {
        if (adminRepository.existsByEmail(registerUserRequestDTO.email())) {
            throw new IllegalArgumentException("Email is already in use");
        }

        Admin newAdmin = Admin.builder()
                .email(registerUserRequestDTO.email())
                .password(passwordEncoder.encode(registerUserRequestDTO.password()))
                .build();
        adminRepository.save(newAdmin);
        return new RegisterUserResponseDTO(newAdmin.getId(), newAdmin.getEmail(), false);
    }

    // Transactional annotation is used to make sure that the method is executed
    // within a transaction
    // Since the user is being deleted "during" it is logged in, we need to make
    // sure that the operation is going thought
    // otherwise we got an error with concurrency
    @Transactional
    @Override
    public DeleteUserResponseDTO deleteAdmin(String email) {
        adminRepository.deleteByEmail(email);
        return new DeleteUserResponseDTO("User was successfully deleted");
    }

    @Override
    public UpdateUserResponseDTO updateAdmin(String email, UpdateUserRequestDTO updateUserRequestDTO) {
        Admin existingAdmin = adminRepository.findByEmail(email).orElse(null);
        // check if User exists
        if (existingAdmin == null) {
            throw new UsernameNotFoundException("User not found");
        }
        // check if new email is not already used by another user
        if (!existingAdmin.getEmail().equals(updateUserRequestDTO.email())
                && adminRepository.existsByEmail(updateUserRequestDTO.email())) {
            throw new IllegalArgumentException("The email already exist");
        }

        existingAdmin.setEmail(updateUserRequestDTO.email());
        existingAdmin.setPassword(passwordEncoder.encode(updateUserRequestDTO.password()));
        adminRepository.save(existingAdmin);

        return new UpdateUserResponseDTO(updateUserRequestDTO.email(), "Update was successful");
    }

    @Override
    public LoginUserResponseDTO loginAdmin(LoginUserRequestDTO loginUserRequestDTO) {
        Admin admin = adminRepository.findByEmail(loginUserRequestDTO.email())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (!passwordEncoder.matches(loginUserRequestDTO.password(), admin.getPassword())) {
            throw new RuntimeException("Password is not valid");
        }
        return new LoginUserResponseDTO(jwtTokenService.generateToken(admin));
    }

    @Override
    public FindUserResponseDTO findAdminByEmail(String email) {
        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new FindUserResponseDTO(admin.getId(), admin.getEmail());
    }
}
