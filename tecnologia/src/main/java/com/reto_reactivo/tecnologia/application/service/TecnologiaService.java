// src/main/java/com/reto_reactivo/tecnologia/application/service/TecnologiaService.java
package com.reto_reactivo.tecnologia.application.service;

import com.reto_reactivo.tecnologia.domain.model.Tecnologia;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TecnologiaService {
    Mono<Tecnologia> registrarTecnologia(Tecnologia tecnologia);
    Flux<Tecnologia> listarTecnologias(int page, int size, String sortDirection);
    // Nuevo método para buscar por ID
    Mono<Tecnologia> findById(Long id);
}
