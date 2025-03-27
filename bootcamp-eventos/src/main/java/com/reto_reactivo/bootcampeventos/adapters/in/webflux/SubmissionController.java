package com.reto_reactivo.bootcampeventos.adapters.in.webflux;

import com.reto_reactivo.bootcampeventos.application.service.submiss.SubmissionService;
import com.reto_reactivo.bootcampeventos.domain.model.Submission;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/submissions")
public class SubmissionController {

    private final SubmissionService submissionService;

    public SubmissionController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    // Endpoint para que un participante envíe su entrega
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('PARTICIPANTE')")
    public Mono<Submission> enviarSubmission(@Valid @RequestBody Submission submission) {
        return submissionService.enviarSubmission(submission);
    }

    // Endpoint para que un tutor o administrador agregue feedback a una entrega
    @PutMapping("/{id}/feedback")
    @PreAuthorize("hasAnyRole('TUTOR', 'ADMIN')")
    public Mono<Submission> agregarFeedback(@PathVariable Long id, @RequestBody String feedback) {
        return submissionService.agregarFeedback(id, feedback);
    }

    // Endpoint para listar todas las entregas de un entregable
    @GetMapping("/entregable/{entregableId}")
    public Flux<Submission> listarSubmissionsPorEntregable(@PathVariable Long entregableId) {
        return submissionService.listarSubmissionsPorEntregable(entregableId);
    }
}
