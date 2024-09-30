package com.example.contacts.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class OpenApiSecurityConfig {

  @Bean
  protected SecurityFilterChain openApiSecurityFilterChain(HttpSecurity http) throws Exception {
    String openApiPath = "/v3/api-docs/**";
    log.info("Creating OpenAPI security config for public access to {}", openApiPath);
    return http
      // only apply security configuration for OpenAPI endpoints
      .securityMatcher(openApiPath)
      // permit public access to OpenAPI endpoints
      .authorizeHttpRequests(c ->
        c.requestMatchers(AntPathRequestMatcher.antMatcher(openApiPath)).permitAll()
      )
      .build();
  }
}
