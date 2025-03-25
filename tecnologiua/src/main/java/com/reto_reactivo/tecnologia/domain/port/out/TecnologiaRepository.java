// src/main/java/com/reto_reactivo/tecnologia/domain/port/out/TecnologiaRepository.java
package com.reto_reactivo.tecnologia.domain.port.out;

import com.reto_reactivo.tecnologia.domain.model.Tecnologia;
import reactor.core.publisher.Mono;

public interface TecnologiaRepository {
    Mono<Tecnologia> save(Tecnologia tecnologia);
    Mono<Boolean> existsByNombre(String nombre);
}
