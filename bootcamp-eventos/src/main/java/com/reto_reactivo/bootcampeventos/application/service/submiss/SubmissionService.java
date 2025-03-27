package com.reto_reactivo.bootcampeventos.application.service.submiss;

import com.reto_reactivo.bootcampeventos.domain.model.Submission;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SubmissionService {
    Mono<Submission> enviarSubmission(Submission submission);
    Mono<Submission> agregarFeedback(Long submissionId, String feedback);
    Flux<Submission> listarSubmissionsPorEntregable(Long entregableId);
}
