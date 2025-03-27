package com.reto_reactivo.bootcampeventos.adapters.out.repository.iteracion;

import com.reto_reactivo.bootcampeventos.domain.model.Iteracion;
import com.reto_reactivo.bootcampeventos.domain.port.out.IteracionRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class IteracionRepositoryImpl implements IteracionRepository {

    private final IteracionReactiveRepository reactiveRepository;

    public IteracionRepositoryImpl(IteracionReactiveRepository reactiveRepository) {
        this.reactiveRepository = reactiveRepository;
    }

    @Override
    public Mono<Iteracion> save(Iteracion iteracion) {
        return reactiveRepository.save(iteracion);
    }

    @Override
    public Mono<Iteracion> findById(Long id) {
        return reactiveRepository.findById(id);
    }

    @Override
    public Flux<Iteracion> findAll() {
        return reactiveRepository.findAll();
    }
}
