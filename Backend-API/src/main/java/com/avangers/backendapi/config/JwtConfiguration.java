package com.avangers.backendapi.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
@Getter
public class JwtConfiguration {

    @Value("${jwt.validity}")
    private long tokenValidity;

    private final RSAPublicKey publicKey;
    private final RSAPrivateKey privateKey;

    public JwtConfiguration() throws NoSuchAlgorithmException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        KeyPair keyPair = generator.generateKeyPair();
        this.publicKey = (RSAPublicKey) keyPair.getPublic();
        this.privateKey = (RSAPrivateKey) keyPair.getPrivate();
    }

}
