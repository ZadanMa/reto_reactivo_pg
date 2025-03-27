package com.reto_reactivo.bootcampeventos.adapters.out.repository.submiss;

import com.reto_reactivo.bootcampeventos.domain.model.Submission;
import com.reto_reactivo.bootcampeventos.domain.port.out.SubmissionRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class SubmissionRepositoryImpl implements SubmissionRepository {

    private final SubmissionReactiveRepository reactiveRepository;

    public SubmissionRepositoryImpl(SubmissionReactiveRepository reactiveRepository) {
        this.reactiveRepository = reactiveRepository;
    }

    @Override
    public Mono<Submission> save(Submission submission) {
        return reactiveRepository.save(submission);
    }

    @Override
    public Mono<Submission> findById(Long id) {
        return reactiveRepository.findById(id);
    }

    @Override
    public Flux<Submission> findAll() {
        return reactiveRepository.findAll();
    }

    @Override
    public Flux<Submission> findByEntregableId(Long entregableId) {
        return reactiveRepository.findByEntregableId(entregableId);
    }
}
