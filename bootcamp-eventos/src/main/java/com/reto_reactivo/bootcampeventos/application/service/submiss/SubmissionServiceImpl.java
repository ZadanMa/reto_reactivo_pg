package com.reto_reactivo.bootcampeventos.application.service.submiss;

import com.reto_reactivo.bootcampeventos.adapters.out.client.UsuarioClient;
import com.reto_reactivo.bootcampeventos.domain.model.Submission;
import com.reto_reactivo.bootcampeventos.domain.port.out.SubmissionRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;

@Service
public class SubmissionServiceImpl implements SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final UsuarioClient usuarioClient;

    public SubmissionServiceImpl(SubmissionRepository submissionRepository, UsuarioClient usuarioClient) {
        this.submissionRepository = submissionRepository;
        this.usuarioClient = usuarioClient;
    }

    @Override
    public Mono<Submission> enviarSubmission(Submission submission) {
        // Al enviar una entrega, se registra la fecha actual.
        submission.setFechaEnvio(LocalDateTime.now());
        return usuarioClient.getUsuarioPorId(submission.getParticipanteId())
                .filter(userDTO -> "PARTICIPANTE".equalsIgnoreCase(userDTO.getRole()))
                .switchIfEmpty(Mono.error(new IllegalArgumentException("El usuario con id " + submission.getParticipanteId() + " no es un participante")))
                .flatMap(userDTO -> {
                    submission.setFechaEnvio(LocalDateTime.now());
                    return submissionRepository.save(submission);
                });
    }

    @Override
    public Mono<Submission> agregarFeedback(Long submissionId, String feedback) {
        // Se busca la submission, se actualiza el feedback y se guarda.
        return submissionRepository.findById(submissionId)
                .flatMap(sub -> {
                    sub.setFeedback(feedback);
                    return submissionRepository.save(sub);
                });
    }

    @Override
    public Flux<Submission> listarSubmissionsPorEntregable(Long entregableId) {
        return submissionRepository.findByEntregableId(entregableId);
    }

}
