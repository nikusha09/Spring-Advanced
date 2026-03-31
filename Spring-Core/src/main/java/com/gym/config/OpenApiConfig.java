package com.gym.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI gymCrmOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Gym CRM API")
                        .description("REST API for Gym CRM system")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Gym CRM Team")))
                .components(new Components()
                        .addSecuritySchemes("usernameHeader",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.APIKEY)
                                        .in(SecurityScheme.In.HEADER)
                                        .name("Username")
                                        .description("Custom username header"))
                        .addSecuritySchemes("passwordHeader",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.APIKEY)
                                        .in(SecurityScheme.In.HEADER)
                                        .name("Password")
                                        .description("Custom password header")));
    }
}
