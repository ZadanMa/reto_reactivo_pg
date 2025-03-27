// src/main/java/com/reto_reactivo/tecnologia/adapters/in/webflux/TecnologiaController.java
package com.reto_reactivo.tecnologia.adapters.in.webflux;

import com.reto_reactivo.tecnologia.application.service.TecnologiaService;
import com.reto_reactivo.tecnologia.domain.exception.DuplicateTechnologyNameException;
import com.reto_reactivo.tecnologia.domain.model.Tecnologia;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/tecnologias")
@Tag(name = "Tecnología", description = "Endpoints para gestionar tecnologías")
public class TecnologiaController {

    private static final Logger logger = LoggerFactory.getLogger(TecnologiaController.class);

    private final TecnologiaService tecnologiaService;

    public TecnologiaController(TecnologiaService tecnologiaService) {
        this.tecnologiaService = tecnologiaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ROLE_TUTOR')")
    @Operation(summary = "Registra una nueva tecnología", description = "Crea una nueva tecnología en el sistema.")
    @ApiResponse(responseCode = "201", description = "Tecnología registrada exitosamente")
    @ApiResponse(responseCode = "409", description = "El nombre de la tecnología ya existe")
    public Mono<Tecnologia> registrarTecnologia(@Valid @RequestBody Tecnologia tecnologia) {
        return tecnologiaService.registrarTecnologia(tecnologia).doOnSuccess(tech -> logger.info("Tecnología registrada exitosamente: {}", tech.getNombre()))
                .doOnError(error -> logger.error("Error al registrar tecnología: {}", error.getMessage()));
    }


    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_TUTOR')")
    @Operation(summary = "Lista las tecnologías", description = "Obtiene una lista paginada y ordenada de tecnologías.")
    @Parameter(name = "page", description = "Número de página", example = "0")
    @Parameter(name = "size", description = "Tamaño de la página", example = "10")
    @Parameter(name = "sortDirection", description = "Dirección de ordenación (asc/desc)", example = "asc")
    public Flux<Tecnologia> listarTecnologias(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        return tecnologiaService.listarTecnologias(page, size, sortDirection);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca una tecnología por ID", description = "Obtiene los detalles de una tecnología específica.")
    @ApiResponse(responseCode = "200", description = "Tecnología encontrada")
    @ApiResponse(responseCode = "404", description = "Tecnología no encontrada")
    public Mono<Tecnologia> findById(@PathVariable Long id) {
        return tecnologiaService.findById(id);
    }

    @ExceptionHandler(DuplicateTechnologyNameException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Mono<ResponseEntity<String>> handleDuplicateName(DuplicateTechnologyNameException ex) {
        return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage()));
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
