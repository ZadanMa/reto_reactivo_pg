package com.reto_reactivo.bootcampeventos.application.service.iteracion;

import com.reto_reactivo.bootcampeventos.domain.model.Iteracion;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IteracionService {
    Mono<Iteracion> crearIteracion(Iteracion iteracion);
    Mono<Iteracion> obtenerIteracion(Long id);
    Flux<Iteracion> listarIteraciones(int page, int size);
    // Método para agregar un entregable (por referencia, p.ej. id del entregable) a una iteración existente
    Mono<Iteracion> agregarEntregableEnIteracion(Long iteracionId, String entregableIdReferencia);

    // Método para inscribir a un participante en una iteración
    Mono<Iteracion> inscribirParticipante(Long iteracionId, Long participanteId);
}
