package com.reto_reactivo.bootcampeventos.adapters.out.repository.entregable;

import com.reto_reactivo.bootcampeventos.domain.model.Entregable;
import com.reto_reactivo.bootcampeventos.domain.port.out.EntregableRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class EntregableRepositoryImpl implements EntregableRepository {

    private final EntregableReactiveRepository reactiveRepository;

    public EntregableRepositoryImpl(EntregableReactiveRepository reactiveRepository) {
        this.reactiveRepository = reactiveRepository;
    }

    @Override
    public Mono<Entregable> save(Entregable entregable) {
        return reactiveRepository.save(entregable);
    }

    @Override
    public Mono<Entregable> findById(Long id) {
        return reactiveRepository.findById(id);
    }

    @Override
    public Flux<Entregable> findAll() {
        return reactiveRepository.findAll();
    }
}
