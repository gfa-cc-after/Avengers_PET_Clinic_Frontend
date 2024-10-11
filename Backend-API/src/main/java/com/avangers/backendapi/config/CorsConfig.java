package com.avangers.backendapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
public class CorsConfig {
  @Value("${cors.urls}")
  String corsUrls;

  @Bean
  public CorsConfiguration corsConfiguration() {
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.setAllowedOrigins(List.of(corsUrls.split(",")));
    corsConfiguration.addAllowedMethod("*");
    corsConfiguration.addAllowedHeader("*");
    return corsConfiguration;
  }
}
