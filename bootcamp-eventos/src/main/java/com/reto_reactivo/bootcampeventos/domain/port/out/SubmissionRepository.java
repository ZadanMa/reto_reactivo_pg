package com.reto_reactivo.bootcampeventos.domain.port.out;

import com.reto_reactivo.bootcampeventos.domain.model.Submission;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SubmissionRepository {
    Mono<Submission> save(Submission submission);
    Mono<Submission> findById(Long id);
    Flux<Submission> findAll();
    // Método adicional para listar submissions por entregable
    Flux<Submission> findByEntregableId(Long entregableId);
}