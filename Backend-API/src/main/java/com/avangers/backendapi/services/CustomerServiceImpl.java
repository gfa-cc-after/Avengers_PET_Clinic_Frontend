package com.avangers.backendapi.services;

import com.avangers.backendapi.DTOs.*;
import com.avangers.backendapi.models.Admin;
import com.avangers.backendapi.models.Customer;
import com.avangers.backendapi.models.User;
import com.avangers.backendapi.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return customerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email is not in database"));
    }

    @Override
    public RegisterUserResponseDTO addCustomer(RegisterUserRequestDTO registerUserRequestDTO) {
        if (customerRepository.existsByEmail(registerUserRequestDTO.email())) {
            throw new IllegalArgumentException("Email is already in use");
        }

        Customer newCustomer = Customer.builder()
                .email(registerUserRequestDTO.email())
                .password(passwordEncoder.encode(registerUserRequestDTO.password()))
                .build();
        customerRepository.save(newCustomer);
        return new RegisterUserResponseDTO(newCustomer.getId(), newCustomer.getEmail(), false);
    }

    @Override
    public DeleteUserResponseDTO deleteCustomer(String email) {
        customerRepository.deleteByEmail(email);
        return new DeleteUserResponseDTO("User was successfully deleted");
    }

    @Override
    public UpdateUserResponseDTO updateCustomer(String email, UpdateUserRequestDTO updateUserRequestDTO) {
        Customer existingCustomer = customerRepository.findByEmail(email).orElse(null);
        // check if User exists
        if (existingCustomer == null) {
            throw new UsernameNotFoundException("User not found");
        }
        // check if new email is not already used by another user
        if (!existingCustomer.getEmail().equals(updateUserRequestDTO.email())
                && customerRepository.existsByEmail(updateUserRequestDTO.email())) {
            throw new IllegalArgumentException("The email already exist");
        }

        existingCustomer.setEmail(updateUserRequestDTO.email());
        existingCustomer.setPassword(passwordEncoder.encode(updateUserRequestDTO.password()));
        customerRepository.save(existingCustomer);

        return new UpdateUserResponseDTO(updateUserRequestDTO.email(), "Update was successful");
    }

    @Override
    public LoginUserResponseDTO loginCustomer(LoginUserRequestDTO loginUserRequestDTO) {
        User user = customerRepository.findByEmail(loginUserRequestDTO.email())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (!passwordEncoder.matches(loginUserRequestDTO.password(), user.getPassword())) {
            throw new RuntimeException("Password is not valid");
        }
        return new LoginUserResponseDTO(jwtTokenService.generateToken(user));
    }

    @Override
    public FindUserResponseDTO findCustomerByEmail(String email) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new FindUserResponseDTO(customer.getId(), customer.getEmail());
    }
}
