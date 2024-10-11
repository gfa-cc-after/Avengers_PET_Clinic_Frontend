package com.avangers.backendapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Configuration
@Getter
public class DeploymentConfiguration {

    @Value("${deployment.url}")
    private String deploymentURL;
}