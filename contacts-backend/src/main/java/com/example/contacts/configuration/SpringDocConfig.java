package com.example.contacts.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

  @Bean
  public OpenAPI openApi() {
    return new OpenAPI()
      .components(
        new Components()
          .addSecuritySchemes(
            "basic",
            new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")
          )
      )
      .addSecurityItem(new SecurityRequirement().addList("basic"));
  }
}
