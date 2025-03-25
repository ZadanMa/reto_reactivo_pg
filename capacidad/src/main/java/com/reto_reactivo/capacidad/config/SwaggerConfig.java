// src/main/java/com/reto_reactivo/capacidad/config/SwaggerConfig.java
package com.reto_reactivo.capacidad.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI capacidadOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("API de Capacidades")
                        .description("Documentación de la API para gestionar capacidades")
                        .version("1.0"));
    }


}
