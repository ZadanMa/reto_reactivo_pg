// src/main/java/com/reto_reactivo/tecnologia/adapters/out/repository/TecnologiaReactiveRepository.java
package com.reto_reactivo.tecnologia.adapters.out.repository;

import com.reto_reactivo.tecnologia.domain.model.Tecnologia;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TecnologiaReactiveRepository extends ReactiveCrudRepository<Tecnologia, Long> {
    Mono<Boolean> existsByNombre(String nombre);

    Flux<Tecnologia> findAll(Sort sort);
}
