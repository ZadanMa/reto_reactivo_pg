// src/main/java/com/reto_reactivo/tecnologia/config/SwaggerConfig.java
package com.reto_reactivo.tecnologia.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI tecnologiaOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("API de Tecnología")
                        .description("Documentación de la API para gestionar tecnologías")
                        .version("1.0"));
    }

}
