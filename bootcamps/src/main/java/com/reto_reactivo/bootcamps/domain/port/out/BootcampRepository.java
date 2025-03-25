package com.reto_reactivo.bootcamps.domain.port.out;

import com.reto_reactivo.bootcamps.domain.model.Bootcamp;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BootcampRepository {
    Mono<Bootcamp> save(Bootcamp bootcamp);
    Mono<Bootcamp> findById(Long id);
    Flux<Bootcamp> findAll();
}
