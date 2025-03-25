// src/main/java/com/reto_reactivo/tecnologia/adapters/in/webflux/TecnologiaController.java
package com.reto_reactivo.tecnologia.adapters.in.webflux;

import com.reto_reactivo.tecnologia.application.service.TecnologiaService;
import com.reto_reactivo.tecnologia.domain.model.Tecnologia;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/tecnologias")
public class TecnologiaController {

    private final TecnologiaService tecnologiaService;

    public TecnologiaController(TecnologiaService tecnologiaService) {
        this.tecnologiaService = tecnologiaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ROLE_TUTOR')")
    public Mono<Tecnologia> registrarTecnologia(@Valid @RequestBody Tecnologia tecnologia) {
        return tecnologiaService.registrarTecnologia(tecnologia);
    }
    // Nuevo endpoint para listar tecnologías con paginación y ordenación
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_TUTOR')")
    public Flux<Tecnologia> listarTecnologias(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        return tecnologiaService.listarTecnologias(page, size, sortDirection);
    }
    // Nuevo endpoint para buscar tecnología por ID
    @GetMapping("/{id}")
    public Mono<Tecnologia> findById(@PathVariable Long id) {
        return tecnologiaService.findById(id);
    }
    @GetMapping("/admin-only")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Mono<String> adminOnlyEndpoint() {
        return Mono.just("¡Hola, ADMIN! Has accedido al endpoint de tecnologia.");
    }

    @GetMapping("/tutor-only")
    @PreAuthorize("hasAuthority('ROLE_TUTOR')")
    public Mono<String> tutorOnlyEndpoint() {
        return Mono.just("¡Hola, TUTOR! Has accedido al endpoint de tecnologia.");
    }
}
