package com.reto_reactivo.bootcamps.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI bootcampsOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("API de Bootcamps")
                        .description("Documentación de la API para gestionar Bootcamps")
                        .version("1.0"));
    }
}
