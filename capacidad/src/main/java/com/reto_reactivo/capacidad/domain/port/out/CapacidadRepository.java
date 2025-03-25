// src/main/java/com/reto_reactivo/capacidad/domain/port/out/CapacidadRepository.java
package com.reto_reactivo.capacidad.domain.port.out;


import com.reto_reactivo.capacidad.domain.model.Capacidad;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CapacidadRepository {
    Mono<Capacidad> save(Capacidad capacidad);
    Mono<Capacidad> findById(Long id);
    // Agrega este método para listar todas las capacidades
    Flux<Capacidad> findAll();
}
