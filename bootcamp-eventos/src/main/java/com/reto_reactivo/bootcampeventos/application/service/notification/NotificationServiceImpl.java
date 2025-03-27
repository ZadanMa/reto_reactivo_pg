package com.reto_reactivo.bootcampeventos.application.service.notification;

import com.reto_reactivo.bootcampeventos.adapters.out.client.UsuarioClient;
import com.reto_reactivo.bootcampeventos.application.dto.UserDTO;
import com.reto_reactivo.bootcampeventos.domain.model.Entregable;
import com.reto_reactivo.bootcampeventos.domain.model.Iteracion;
import com.reto_reactivo.bootcampeventos.domain.model.Submission;

import com.reto_reactivo.bootcampeventos.domain.port.out.EntregableRepository;
import com.reto_reactivo.bootcampeventos.domain.port.out.IteracionRepository;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final JavaMailSender mailSender;
    private final UsuarioClient usuarioClient;
    private final IteracionRepository iteracionRepository;
    private final EntregableRepository entregableRepository;

    // Correo fijo del administrador
    private static final String ADMIN_EMAIL = "adan.moreto@pragma.com.co";

    public NotificationServiceImpl(JavaMailSender mailSender, UsuarioClient usuarioClient,
                                   IteracionRepository iteracionRepository, EntregableRepository entregableRepository) {
        this.mailSender = mailSender;
        this.usuarioClient = usuarioClient;
        this.iteracionRepository = iteracionRepository;
        this.entregableRepository = entregableRepository;
    }

    @Override
    @Retry(name = "mailService")
    public Mono<Void> notifyIterationCreation(Iteracion iteracion) {
        // Genera un enlace de invitación para la iteración
        String token = UUID.randomUUID().toString();
        String enlace = "http://localhost:8083/api/iteraciones/invitacion/" + iteracion.getId() + "?token=" + token;

        // Obtener correos de los tutores a partir de sus IDs
        Mono<List<String>> correosTutores = Flux.fromIterable(iteracion.getTutorIds())
                .flatMap(usuarioClient::getUsuarioPorId)
                .map(UserDTO::getEmail)
                .collectList();

        // Obtener correos de los participantes (si existen)
        Mono<List<String>> correosParticipantes = (iteracion.getParticipanteIds() == null || iteracion.getParticipanteIds().isEmpty())
                ? Mono.just(List.of())
                : Flux.fromIterable(iteracion.getParticipanteIds())
                .flatMap(usuarioClient::getUsuarioPorId)
                .map(UserDTO::getEmail)
                .collectList();

        return Mono.zip(correosTutores, correosParticipantes)
                .flatMap(tuple -> {
                    List<String> emailsTutores = tuple.getT1();
                    List<String> emailsParticipantes = tuple.getT2();

                    // Enviar correo a administrador
                    SimpleMailMessage messageAdmin = new SimpleMailMessage();
                    messageAdmin.setTo(ADMIN_EMAIL);
                    messageAdmin.setSubject("Nueva Iteración de Bootcamp");
                    messageAdmin.setText("Se ha creado una nueva iteración para el bootcamp con id " + iteracion.getBootcampId() +
                            "\nFecha: " + iteracion.getFecha() +
                            "\nCupo: " + iteracion.getCupoMaximo() +
                            "\nEnlace de inscripción: " + enlace);
                    mailSender.send(messageAdmin);

                    // Enviar correo a tutores
                    if (!emailsTutores.isEmpty()) {
                        SimpleMailMessage messageTutores = new SimpleMailMessage();
                        messageTutores.setTo(emailsTutores.toArray(new String[0]));
                        messageTutores.setSubject("Nueva Iteración Asignada");
                        messageTutores.setText("Se te ha asignado una nueva iteración para el bootcamp con id " + iteracion.getBootcampId() +
                                "\nFecha: " + iteracion.getFecha() +
                                "\nEnlace de inscripción: " + enlace);
                        mailSender.send(messageTutores);
                    }

                    // Enviar correo a participantes, si los hay
                    if (!emailsParticipantes.isEmpty()) {
                        SimpleMailMessage messageParticipantes = new SimpleMailMessage();
                        messageParticipantes.setTo(emailsParticipantes.toArray(new String[0]));
                        messageParticipantes.setSubject("Confirmación de Inscripción a Iteración");
                        messageParticipantes.setText("Te has inscrito en la iteración para el bootcamp con id " + iteracion.getBootcampId() +
                                "\nFecha: " + iteracion.getFecha());
                        mailSender.send(messageParticipantes);
                    }
                    return Mono.empty();
                })
                .subscribeOn(Schedulers.boundedElastic())
                .then();
    }

    @Override
    @Retry(name = "mailService")
    public Mono<Void> notifyEntregableAssignment(Entregable entregable) {
        // Obtener el tutor que asigna el entregable
        Mono<UserDTO> tutorMono = usuarioClient.getUsuarioPorId(entregable.getTutorId());

        // Obtener la iteración asociada al entregable para extraer los participantes
        Mono<List<String>> correosParticipantes = iteracionRepository.findById(entregable.getIteracionId())
                .flatMapMany(iteracion -> {
                    List<Long> participantes = iteracion.getParticipanteIds();
                    return (participantes == null || participantes.isEmpty())
                            ? Flux.empty()
                            : Flux.fromIterable(participantes);
                })
                .flatMap(usuarioClient::getUsuarioPorId)
                .map(UserDTO::getEmail)
                .collectList();

        return Mono.zip(tutorMono, correosParticipantes)
                .flatMap(tuple -> {
                    UserDTO tutor = tuple.getT1();
                    List<String> emailsParticipantes = tuple.getT2();
                    return Mono.fromRunnable(() -> {
                        if (!emailsParticipantes.isEmpty()) {
                            SimpleMailMessage message = new SimpleMailMessage();
                            message.setTo(emailsParticipantes.toArray(new String[0]));
                            message.setSubject("Nuevo Entregable Asignado");
                            message.setText("El tutor " + tutor.getUsername() +
                                    " ha asignado un nuevo entregable: " + entregable.getTitulo() +
                                    "\nFecha límite: " + entregable.getFechaLimite());
                            mailSender.send(message);
                        }
                    }).subscribeOn(Schedulers.boundedElastic());
                }).then();
    }


    @Override
    @Retry(name = "mailService")
    public Mono<Void> notifySubmissionReceived(Submission submission) {
        // Primero, se obtiene el entregable asociado a la submission
        return entregableRepository.findById(submission.getEntregableId())
                .switchIfEmpty(Mono.error(new IllegalArgumentException(
                        "Entregable con id " + submission.getEntregableId() + " no encontrado")))
                .flatMap(entregable ->
                        // Luego, se obtiene el tutor que asignó el entregable
                        usuarioClient.getUsuarioPorId(entregable.getTutorId())
                                .flatMap(tutor -> Mono.fromRunnable(() -> {
                                    SimpleMailMessage message = new SimpleMailMessage();
                                    // Se notifica al tutor utilizando el correo obtenido
                                    message.setTo(tutor.getEmail());
                                    message.setSubject("Nueva Submission Recibida");
                                    message.setText("El participante con id " + submission.getParticipanteId() +
                                            " ha enviado su entrega para el entregable '" + entregable.getTitulo() + "'.");
                                    mailSender.send(message);
                                }).subscribeOn(Schedulers.boundedElastic()))
                )
                .then();
    }

    @Override
    @Retry(name = "mailService")
    public Mono<Void> notifyFeedbackPublished(Submission submission) {
        return usuarioClient.getUsuarioPorId(submission.getParticipanteId())
                .flatMap(participante -> Mono.fromCallable(() -> {
                    SimpleMailMessage message = new SimpleMailMessage();
                    message.setTo(participante.getEmail());
                    message.setSubject("Feedback de tu Entrega");
                    message.setText("Tu entrega para el entregable con id " + submission.getEntregableId() +
                            " ha recibido el siguiente feedback: " + submission.getFeedback());
                    mailSender.send(message);
                    return Void.TYPE;
                }).subscribeOn(Schedulers.boundedElastic()))
                .then();
    }
}
