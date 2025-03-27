package com.reto_reactivo.bootcampeventos.application.service.notification;

import com.reto_reactivo.bootcampeventos.domain.model.Entregable;
import com.reto_reactivo.bootcampeventos.domain.model.Iteracion;
import com.reto_reactivo.bootcampeventos.domain.model.Submission;
import reactor.core.publisher.Mono;

public interface NotificationService {
    Mono<Void> notifyIterationCreation(Iteracion iteracion);
    Mono<Void> notifyEntregableAssignment(Entregable entregable);
    Mono<Void> notifySubmissionReceived(Submission submission);
    Mono<Void> notifyFeedbackPublished(Submission submission);
}
