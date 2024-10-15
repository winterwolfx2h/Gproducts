package com.GestionProduit.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class OpenApiConfig implements WebMvcConfigurer {

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info(new Info().title("Your API Title").version("1.0").description("API description"))
        .addSecurityItem(new SecurityRequirement().addList("Bearer"))
        .components(
            new io.swagger.v3.oas.models.Components()
                .addSecuritySchemes(
                    "Bearer", new SecurityScheme().type(Type.HTTP).scheme("bearer").bearerFormat("JWT")));
  }
}
