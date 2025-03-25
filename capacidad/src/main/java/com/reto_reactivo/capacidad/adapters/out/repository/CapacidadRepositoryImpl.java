// src/main/java/com/reto_reactivo/capacidad/adapters/out/repository/CapacidadRepositoryImpl.java
package com.reto_reactivo.capacidad.adapters.out.repository;

import com.reto_reactivo.capacidad.domain.model.Capacidad;
import com.reto_reactivo.capacidad.domain.port.out.CapacidadRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.stereotype.Repository;

@Repository
public class CapacidadRepositoryImpl implements CapacidadRepository {

    private final CapacidadReactiveRepository reactiveRepository;

    public CapacidadRepositoryImpl(CapacidadReactiveRepository reactiveRepository) {
        this.reactiveRepository = reactiveRepository;
    }

    @Override
    public Mono<Capacidad> save(Capacidad capacidad) {
        return reactiveRepository.save(capacidad);
    }

    @Override
    public Mono<Capacidad> findById(Long id) {
        return reactiveRepository.findById(id);
    }

    @Override
    public Flux<Capacidad> findAll() {
        return reactiveRepository.findAll();
    }

}
