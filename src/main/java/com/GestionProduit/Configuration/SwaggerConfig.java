package com.GestionProduit.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SwaggerConfig implements WebMvcConfigurer {

  @Bean
  public OpenAPI customOpenAPI() {
    License mitLicense = new License().name("CrackHead Website").url("https://opensource.org/licenses/MIT");

    Info info =
        new Info()
            .title("CrackHead")
            .description(
                "CrackHead is an open source project that provides quality crack for rather say retarded kids.")
            .version("1.0.0 retarded.")
            .license(mitLicense);

    SecurityScheme securityScheme =
        new SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT")
            .name("JWT")
            .in(SecurityScheme.In.HEADER);

    return new OpenAPI()
        .info(info)
        .components(new io.swagger.v3.oas.models.Components().addSecuritySchemes("bearer-key", securityScheme))
        .addSecurityItem(new SecurityRequirement().addList("bearer-key"));
  }

  @Override
  public void addViewControllers(final ViewControllerRegistry registry) {
    registry.addRedirectViewController("/", "/swagger-ui/index.html");
  }
}
