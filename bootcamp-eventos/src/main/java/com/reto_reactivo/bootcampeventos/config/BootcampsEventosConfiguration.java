// BootcampsEventosConfiguration.java
package com.reto_reactivo.bootcampeventos.config;

import com.reto_reactivo.bootcampeventos.adapters.out.client.BootcampClient;
import com.reto_reactivo.bootcampeventos.adapters.out.client.UsuarioClient;
import com.reto_reactivo.bootcampeventos.application.service.entregable.EntregableService;
import com.reto_reactivo.bootcampeventos.application.service.entregable.EntregableServiceImpl;
import com.reto_reactivo.bootcampeventos.application.service.iteracion.IteracionService;
import com.reto_reactivo.bootcampeventos.application.service.iteracion.IteracionServiceImpl;
import com.reto_reactivo.bootcampeventos.application.service.submiss.SubmissionService;
import com.reto_reactivo.bootcampeventos.application.service.submiss.SubmissionServiceImpl;
import com.reto_reactivo.bootcampeventos.domain.port.out.EntregableRepository;
import com.reto_reactivo.bootcampeventos.domain.port.out.IteracionRepository;
import com.reto_reactivo.bootcampeventos.domain.port.out.SubmissionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BootcampsEventosConfiguration {

    @Bean
    public EntregableService entregableService(EntregableRepository entregableRepository, IteracionRepository iteracionRepository) {
        return new EntregableServiceImpl(entregableRepository, iteracionRepository);
    }
    @Bean
    public SubmissionService submissionService(SubmissionRepository submissionRepository, UsuarioClient usuarioClient) {
        return new SubmissionServiceImpl(submissionRepository, usuarioClient);
    }
}