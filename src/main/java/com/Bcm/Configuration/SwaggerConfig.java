package com.Bcm.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for Swagger API documentation. Implements the WebMvcConfigurer interface to customize Spring Web
 * MVC behavior.
 */
@Configuration
public class SwaggerConfig implements WebMvcConfigurer {

    /**
     * Creates and returns a custom OpenAPI bean with the specified configurations.
     *
     * @return The custom OpenAPI bean.
     */
    @Bean
    public OpenAPI customOpenAPI() {

        License mitLicense = new License().name(Extern.SWAGGER_LICENSE).url(Extern.SWAGGER_LICENSE_URL);

        Info info =
                new Info()
                        .title(Extern.SWAGGER_TITLE)
                        .description(Extern.SWAGGER_DESCRIPTION)
                        .license(mitLicense)
                        .version(Extern.SWAGGER_VERSION);
        return new OpenAPI().info(info);
    }

    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/swagger-ui.html");
        registry.addRedirectViewController("/swagger-ui", "billcom");
    }
}
