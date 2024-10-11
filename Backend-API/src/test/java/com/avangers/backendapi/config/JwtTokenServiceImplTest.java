package com.avangers.backendapi.config;

import com.avangers.backendapi.models.Customer;
import com.avangers.backendapi.services.CustomerService;
import com.avangers.backendapi.services.CustomerServiceImpl;
import com.avangers.backendapi.services.JwtTokenService;
import com.avangers.backendapi.services.JwtTokenServiceImpl;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ImportAutoConfiguration
@ComponentScan("com.avangers.backendapi")
@ContextConfiguration(classes = { SecurityConfig.class, CustomerService.class, CustomerServiceImpl.class })
class JwtTokenServiceImplTest {

    @Autowired
    private JwtEncoder encoder;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtDecoder jwtDecoder;

    @Autowired
    JwtConfiguration jwtConfiguration;

    @Test
    @DisplayName("Should extract payload from the token ")
    @WithMockUser
    void shouldReturnPayloadFromToken() {

        JwtTokenService jwtTokenServiceImpl = new JwtTokenServiceImpl(encoder, jwtConfiguration);

        Customer user = Customer
                .builder()
                .id(1L)
                .email("john.doe@gmail.com")
                .password(passwordEncoder.encode("password"))
                .build();
        String token = jwtTokenServiceImpl.generateToken(user);

        Jwt payload = jwtDecoder.decode(token);
        String subject = payload.getSubject();
        Assertions.assertEquals(subject, user.getEmail());
    }
}