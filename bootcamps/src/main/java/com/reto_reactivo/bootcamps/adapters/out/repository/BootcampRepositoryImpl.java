package com.reto_reactivo.bootcamps.adapters.out.repository;

import com.reto_reactivo.bootcamps.domain.model.Bootcamp;
import com.reto_reactivo.bootcamps.domain.port.out.BootcampRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class BootcampRepositoryImpl implements BootcampRepository {

    private final BootcampReactiveRepository reactiveRepository;

    public BootcampRepositoryImpl(BootcampReactiveRepository reactiveRepository) {
        this.reactiveRepository = reactiveRepository;
    }

    @Override
    public Mono<Bootcamp> save(Bootcamp bootcamp) {
        return reactiveRepository.save(bootcamp);
    }

    @Override
    public Mono<Bootcamp> findById(Long id) {
        return reactiveRepository.findById(id);
    }

    @Override
    public Flux<Bootcamp> findAll() {
        return reactiveRepository.findAll();
    }
}
