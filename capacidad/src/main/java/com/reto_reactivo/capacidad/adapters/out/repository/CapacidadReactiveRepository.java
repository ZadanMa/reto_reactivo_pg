// src/main/java/com/reto_reactivo/capacidad/adapters/out/repository/CapacidadReactiveRepository.java
package com.reto_reactivo.capacidad.adapters.out.repository;

import com.reto_reactivo.capacidad.domain.model.Capacidad;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface CapacidadReactiveRepository extends ReactiveCrudRepository<Capacidad, Long> {
    // Métodos personalizados si se requieren
    Mono<Boolean> existsByNombre(String nombre);
}
