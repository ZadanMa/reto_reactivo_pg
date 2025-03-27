package com.reto_reactivo.bootcampeventos.domain.port.out;

import com.reto_reactivo.bootcampeventos.domain.model.Iteracion;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IteracionRepository {
    Mono<Iteracion> save(Iteracion iteracion);
    Mono<Iteracion> findById(Long id);
    Flux<Iteracion> findAll();
}
