package com.reto_reactivo.bootcampeventos.application.service.iteracion;

import com.reto_reactivo.bootcampeventos.adapters.out.client.BootcampClient;
import com.reto_reactivo.bootcampeventos.adapters.out.client.UsuarioClient;
import com.reto_reactivo.bootcampeventos.application.service.notification.NotificationService;
import com.reto_reactivo.bootcampeventos.domain.model.Iteracion;
import com.reto_reactivo.bootcampeventos.domain.port.out.IteracionRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class IteracionServiceImpl implements IteracionService {

    private final IteracionRepository iteracionRepository;
    private final UsuarioClient usuarioClient;
    private final BootcampClient bootcampClient;
    private final NotificationService notificationService;

    public IteracionServiceImpl(IteracionRepository iteracionRepository, UsuarioClient usuarioClient, BootcampClient bootcampClient, NotificationService notificationService) {
        this.iteracionRepository = iteracionRepository;
        this.usuarioClient = usuarioClient;
        this.bootcampClient = bootcampClient;
        this.notificationService = notificationService;
    }

    public Mono<Iteracion> crearIteracion(Iteracion iteracion) {
        // Validar que el bootcamp exista
        return bootcampClient.obtenerBootcampPorId(iteracion.getBootcampId())
                .switchIfEmpty(Mono.error(new IllegalArgumentException("El bootcamp con id " + iteracion.getBootcampId() + " no existe")))
                .flatMap(bootcampDTO ->
                        // Validar que cada tutorId corresponde a un usuario con rol TUTOR
                        Flux.fromIterable(iteracion.getTutorIds())
                                .flatMap(tutorId ->
                                        usuarioClient.getUsuarioPorId(tutorId)
                                                .filter(userDTO -> "TUTOR".equalsIgnoreCase(userDTO.getRole()))
                                                .switchIfEmpty(Mono.error(new IllegalArgumentException("El usuario con id " + tutorId + " no es un tutor")))
                                )
                                .then(iteracionRepository.save(iteracion))
                )
                .flatMap(savedIteracion ->
                        // Una vez guardada la iteración, enviar la notificación de creación
                        notificationService.notifyIterationCreation(savedIteracion)
                                // Luego retornar la iteración guardada
                                .thenReturn(savedIteracion)
                );
    }


    @Override
    public Mono<Iteracion> obtenerIteracion(Long id) {
        return iteracionRepository.findById(id);
    }

    @Override
    public Flux<Iteracion> listarIteraciones(int page, int size) {
        return iteracionRepository.findAll()
                .skip((long) page * size)
                .take(size);
    }
    @Override
    public Mono<Iteracion> agregarEntregableEnIteracion(Long iteracionId, String entregableIdReferencia) {
        return iteracionRepository.findById(iteracionId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Iteración no encontrada")))
                .flatMap(iteracion -> {
                    // Suponiendo que 'entregables' es una lista de Strings que se actualiza
                    List<String> lista = iteracion.getEntregables();
                    if (lista == null) {
                        lista = new ArrayList<>();
                    }
                    lista.add(entregableIdReferencia);
                    iteracion.setEntregables(lista);
                    return iteracionRepository.save(iteracion);
                });
    }

    @Override
    public Mono<Iteracion> inscribirParticipante(Long iteracionId, Long participanteId) {
        return iteracionRepository.findById(iteracionId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Iteración no encontrada")))
                .flatMap(iteracion -> {
                    List<Long> participantes = iteracion.getParticipanteIds();
                    if (participantes == null) {
                        participantes = new ArrayList<>();
                    }
                    if (!participantes.contains(participanteId)) {
                        participantes.add(participanteId);
                        iteracion.setParticipanteIds(participantes);
                    }
                    return iteracionRepository.save(iteracion);
                });
    }

}
