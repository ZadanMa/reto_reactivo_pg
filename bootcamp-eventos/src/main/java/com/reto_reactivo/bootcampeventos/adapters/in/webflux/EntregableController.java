package com.reto_reactivo.bootcampeventos.adapters.in.webflux;

import com.reto_reactivo.bootcampeventos.application.service.entregable.EntregableService;
import com.reto_reactivo.bootcampeventos.domain.model.Entregable;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/entregables")
public class EntregableController {

    private final EntregableService entregableService;

    public EntregableController(EntregableService entregableService) {
        this.entregableService = entregableService;
    }

    // Endpoint para que un tutor o administrador cree un entregable
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('TUTOR', 'ADMIN')")
    public Mono<Entregable> crearEntregable(@Valid @RequestBody Entregable entregable) {
        return entregableService.crearEntregable(entregable);
    }

    // Endpoint para obtener un entregable por su id
    @GetMapping("/{id}")
    public Mono<Entregable> obtenerEntregable(@PathVariable Long id) {
        return entregableService.obtenerEntregable(id);
    }

    // Endpoint para listar los entregables de una iteración
    @GetMapping("/iteracion/{iteracionId}")
    public Flux<Entregable> listarEntregablesPorIteracion(@PathVariable Long iteracionId) {
        return entregableService.listarEntregablesPorIteracion(iteracionId);
    }
}
