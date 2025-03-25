package com.reto_reactivo.bootcamps.config;

import com.reto_reactivo.bootcamps.application.service.BootcampService;
import com.reto_reactivo.bootcamps.application.service.BootcampServiceImpl;
import com.reto_reactivo.bootcamps.domain.port.out.BootcampRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BootcampsConfiguration {

    @Bean
    public BootcampService bootcampService(BootcampRepository bootcampRepository) {
        return new BootcampServiceImpl(bootcampRepository);
    }
}
