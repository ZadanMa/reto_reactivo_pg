// src/main/java/com/reto_reactivo/tecnologia/application/service/TecnologiaService.java
package com.reto_reactivo.tecnologia.application.service;

import com.reto_reactivo.tecnologia.domain.model.Tecnologia;
import reactor.core.publisher.Mono;

public interface TecnologiaService {
    Mono<Tecnologia> registrarTecnologia(Tecnologia tecnologia);
}
