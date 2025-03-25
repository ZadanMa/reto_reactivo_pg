// src/main/java/com/reto_reactivo/tecnologia/config/TecnologiaConfiguration.java
package com.reto_reactivo.tecnologia.config;

import com.reto_reactivo.tecnologia.application.service.TecnologiaService;
import com.reto_reactivo.tecnologia.application.service.TecnologiaServiceImpl;
import com.reto_reactivo.tecnologia.domain.port.out.TecnologiaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TecnologiaConfiguration {

    @Bean
    public TecnologiaService tecnologiaService(TecnologiaRepository tecnologiaRepository) {
        return new TecnologiaServiceImpl(tecnologiaRepository);
    }
}
