// src/main/java/com/reto_reactivo/capacidad/config/CapacidadConfiguration.java
package com.reto_reactivo.capacidad.config;

import com.reto_reactivo.capacidad.adapters.out.client.TecnologiaClient;
import com.reto_reactivo.capacidad.application.service.CapacidadService;
import com.reto_reactivo.capacidad.application.service.CapacidadServiceImpl;
import com.reto_reactivo.capacidad.domain.port.out.CapacidadRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CapacidadConfiguration {

    @Bean
    public CapacidadService capacidadService(CapacidadRepository capacidadRepository,
                                             TecnologiaClient tecnologiaClient) {
        // Ahora pasas ambos parámetros al constructor
        return new CapacidadServiceImpl(capacidadRepository, tecnologiaClient);
    }
}

