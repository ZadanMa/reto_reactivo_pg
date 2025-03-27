// src/main/java/com/reto_reactivo/tecnologia/config/SwaggerConfig.java
package com.reto_reactivo.tecnologia.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI tecnologiaOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Tecnología")
                        .description("Documentación de la API para gestionar tecnologías. Incluye endpoints para registrar, listar y buscar tecnologías.")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Adan Moreto")
                                .email("adan.moreto@pragma.com.co")
                                .url("https://www.linkedin.com/in/adan-moreto-763495320/"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")));
    }


}
