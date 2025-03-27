package com.reto_reactivo.bootcampeventos.adapters.in.webflux;

import com.reto_reactivo.bootcampeventos.application.service.iteracion.IteracionService;
import com.reto_reactivo.bootcampeventos.domain.model.Iteracion;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/iteraciones")
public class IteracionController {

    private final IteracionService iteracionService;

    public IteracionController(IteracionService iteracionService) {
        this.iteracionService = iteracionService;
    }

    // Solo el ADMIN puede crear iteraciones
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<Iteracion> crearIteracion(@Valid @RequestBody Iteracion iteracion) {
        return iteracionService.crearIteracion(iteracion);
    }

    // Obtener detalle de una iteración
    @GetMapping("/{id}")
    public Mono<Iteracion> obtenerIteracion(@PathVariable Long id) {
        return iteracionService.obtenerIteracion(id);
    }

    // Listar iteraciones con paginación
    @GetMapping
    public Flux<Iteracion> listarIteraciones(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size) {
        return iteracionService.listarIteraciones(page, size);
    }
    // Agregar un entregable a una iteración (por ejemplo, para actualizar la lista de entregables)
    @PostMapping("/{iteracionId}/entregables")
    @PreAuthorize("hasAnyRole('TUTOR','ADMIN')")
    public Mono<Iteracion> agregarEntregable(@PathVariable Long iteracionId,
                                             @RequestBody String entregableIdReferencia) {
        return iteracionService.agregarEntregableEnIteracion(iteracionId, entregableIdReferencia);
    }

    // Inscribir un participante a una iteración (por ejemplo, mediante enlace de invitación)
    @PostMapping("/{iteracionId}/inscribir")
    @PreAuthorize("hasRole('PARTICIPANTE')")
    public Mono<Iteracion> inscribirParticipante(@PathVariable Long iteracionId,
                                                 @RequestParam Long participanteId) {
        return iteracionService.inscribirParticipante(iteracionId, participanteId);
    }
}
