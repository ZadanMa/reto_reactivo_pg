// src/main/java/com/reto_reactivo/capacidad/application/service/CapacidadService.java
package com.reto_reactivo.capacidad.application.service;

import com.reto_reactivo.capacidad.application.dto.CapacidadDTO;
import com.reto_reactivo.capacidad.domain.model.Capacidad;
import com.reto_reactivo.capacidad.domain.port.in.RegistrarCapacidadUseCase;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

public interface CapacidadService extends RegistrarCapacidadUseCase {
    // Se pueden agregar otros métodos de consulta o actualización
    Mono<Capacidad> registrarCapacidad(Capacidad capacidad);

    // Nuevo método para agregar tecnologías a una capacidad existente
    Mono<Capacidad> agregarTecnologias(Long capacidadId, Set<Long> nuevasTecnologias);

    Flux<CapacidadDTO> listarCapacidades(int page, int size, String sortField, String sortDirection);

    Mono<Capacidad> findById(Long id);
    Mono<CapacidadDTO> obtenerCapacidadDetalle(Long id);
}

