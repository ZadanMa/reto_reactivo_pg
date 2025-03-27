package com.reto_reactivo.bootcampeventos.domain.port.out;

import com.reto_reactivo.bootcampeventos.domain.model.Entregable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EntregableRepository {
    Mono<Entregable> save(Entregable entregable);
    Mono<Entregable> findById(Long id);
    Flux<Entregable> findAll();
}