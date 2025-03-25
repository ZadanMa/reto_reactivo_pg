// src/main/java/com/reto_reactivo/capacidad/adapters/in/webflux/CapacidadController.java
package com.reto_reactivo.capacidad.adapters.in.webflux;

import com.reto_reactivo.capacidad.application.dto.CapacidadDTO;
import com.reto_reactivo.capacidad.application.service.CapacidadService;
import com.reto_reactivo.capacidad.domain.model.Capacidad;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

@RestController
@RequestMapping("/api/capacidades")
public class CapacidadController {

    private final CapacidadService capacidadService;

    public CapacidadController(CapacidadService capacidadService) {
        this.capacidadService = capacidadService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Capacidad> registrarCapacidad(@Valid @RequestBody Capacidad capacidad) {
        return capacidadService.registrarCapacidad(capacidad);
    }
    @PatchMapping("/{id}/tecnologias")
    public Mono<Capacidad> agregarTecnologias(
            @PathVariable Long id,
            @RequestBody Set<Long> nuevasTecnologias) {
        return capacidadService.agregarTecnologias(id, nuevasTecnologias);
    }
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_TUTOR')")
    public Flux<CapacidadDTO> listarCapacidades(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nombre") String sortField,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        return capacidadService.listarCapacidades(page, size, sortField, sortDirection);
    }
    @GetMapping("/{id}")
    public Mono<CapacidadDTO> findById(@PathVariable Long id) {
        return capacidadService.obtenerCapacidadDetalle(id);
    }
}
