package com.reto_reactivo.bootcampeventos.application.service.entregable;

import com.reto_reactivo.bootcampeventos.domain.model.Entregable;
import com.reto_reactivo.bootcampeventos.domain.port.out.EntregableRepository;
import com.reto_reactivo.bootcampeventos.domain.port.out.IteracionRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EntregableServiceImpl implements EntregableService {

    private final EntregableRepository entregableRepository;
    private final IteracionRepository iteracionRepository;

    public EntregableServiceImpl(EntregableRepository entregableRepository, IteracionRepository iteracionRepository) {
        this.entregableRepository = entregableRepository;
        this.iteracionRepository = iteracionRepository;
    }

    @Override
    public Mono<Entregable> crearEntregable(Entregable entregable) {
        // Se consulta la iteración a la que pertenece el entregable
        return iteracionRepository.findById(entregable.getIteracionId())
                .switchIfEmpty(Mono.error(new IllegalArgumentException("La iteración no existe")))
                .flatMap(iteracion -> {
                    // Validar que el tutorId que se envía esté incluido en la lista de tutorIds de la iteración
                    if (!iteracion.getTutorIds().contains(entregable.getTutorId())) {
                        return Mono.error(new IllegalArgumentException("El tutor con id " + entregable.getTutorId() + " no está asignado a esta iteración"));
                    }
                    return entregableRepository.save(entregable);
                });
    }


    @Override
    public Mono<Entregable> obtenerEntregable(Long id) {
        return entregableRepository.findById(id);
    }

    @Override
    public Flux<Entregable> listarEntregablesPorIteracion(Long iteracionId) {
        // Suponemos que el repositorio no tiene un método directo, se filtra sobre findAll()
        return entregableRepository.findAll()
                .filter(e -> e.getIteracionId().equals(iteracionId));
    }
}
