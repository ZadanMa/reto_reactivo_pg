package com.reto_reactivo.bootcampeventos.adapters.out.repository.submiss;

import com.reto_reactivo.bootcampeventos.domain.model.Submission;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface SubmissionReactiveRepository extends ReactiveCrudRepository<Submission, Long> {
    Flux<Submission> findByEntregableId(Long entregableId);
}
