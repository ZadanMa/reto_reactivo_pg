// src/main/java/com/reto_reactivo/capacidad/domain/port/in/RegistrarCapacidadUseCase.java
package com.reto_reactivo.capacidad.domain.port.in;

import com.reto_reactivo.capacidad.domain.model.Capacidad;
import reactor.core.publisher.Mono;

public interface RegistrarCapacidadUseCase {
    Mono<Capacidad> registrarCapacidad(Capacidad capacidad);
}
