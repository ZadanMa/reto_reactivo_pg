// src/main/java/com/reto_reactivo/tecnologia/adapters/out/repository/TecnologiaRepositoryImpl.java
package com.reto_reactivo.tecnologia.adapters.out.repository;

import com.reto_reactivo.tecnologia.domain.model.Tecnologia;
import com.reto_reactivo.tecnologia.domain.port.out.TecnologiaRepository;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.stereotype.Repository;

@Repository
public class TecnologiaRepositoryImpl implements TecnologiaRepository {

    private final TecnologiaReactiveRepository reactiveRepository;

    public TecnologiaRepositoryImpl(TecnologiaReactiveRepository reactiveRepository) {
        this.reactiveRepository = reactiveRepository;
    }

    @Override
    public Mono<Tecnologia> save(Tecnologia tecnologia) {
        return reactiveRepository.save(tecnologia);
    }

    @Override
    public Mono<Boolean> existsByNombre(String nombre) {
        return reactiveRepository.existsByNombre(nombre);
    }

    @Override
    public Flux<Tecnologia> findAllTecnologias(Sort sort) {
        return reactiveRepository.findAll(sort);
    }
}
