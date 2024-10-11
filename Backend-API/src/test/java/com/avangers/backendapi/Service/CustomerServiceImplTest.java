package com.avangers.backendapi.Service;

import com.avangers.backendapi.DTOs.UpdateUserRequestDTO;
import com.avangers.backendapi.DTOs.UpdateUserResponseDTO;
import com.avangers.backendapi.models.Customer;
import com.avangers.backendapi.repositories.CustomerRepository;
import com.avangers.backendapi.services.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class CustomerServiceImplTest {
    @Mock
    private CustomerRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CustomerServiceImpl userServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Should return 'User not found' if user does not exist")
    @Test
    void shouldReturnUserNotFound() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            userServiceImpl.updateCustomer("old@asdad.cy",
                    new UpdateUserRequestDTO("newemail@email.com", "NewPassword123"));
        });
    }

    @DisplayName("Should return 'Update was successful' if email is changed")
    @Test
    void shouldUpdateUserData() {
        Customer testUser = new Customer();
        testUser.setEmail("oldeamil@eamil.com");

        when(userRepository.findByEmail("oldeamil@eamil.com")).thenReturn(Optional.of(testUser));
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode("NewPassword123")).thenReturn("encodedPassword");

        UpdateUserResponseDTO result = userServiceImpl.updateCustomer("oldeamil@eamil.com",
                new UpdateUserRequestDTO("newemail@eamil.com", "NewPassword123"));

        assertEquals("Update was successful", result.message());
    }
}
