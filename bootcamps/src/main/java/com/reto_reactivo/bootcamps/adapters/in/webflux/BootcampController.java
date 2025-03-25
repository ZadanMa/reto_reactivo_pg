package com.reto_reactivo.bootcamps.adapters.in.webflux;

import com.reto_reactivo.bootcamps.application.dto.BootcampDetailsDTO;
import com.reto_reactivo.bootcamps.application.service.BootcampService;
import com.reto_reactivo.bootcamps.domain.model.Bootcamp;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/bootcamps")
public class BootcampController {

    private final BootcampService bootcampService;

    public BootcampController(BootcampService bootcampService) {
        this.bootcampService = bootcampService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Bootcamp> registrarBootcamp(@Valid @RequestBody Bootcamp bootcamp) {
        return bootcampService.registrarBootcamp(bootcamp);
    }

    @GetMapping("orden")
    public Flux<Bootcamp> listarBootcamps(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nombre") String sortField,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        return bootcampService.listarBootcamps(page, size, sortField, sortDirection);
    }
    // Endpoint para obtener un bootcamp por su ID
    // Nuevo endpoint individual: obtener un bootcamp con detalles
    @GetMapping("/{id}")
    public Mono<BootcampDetailsDTO> obtenerBootcampConDetalles(@PathVariable Long id) {
        return bootcampService.obtenerBootcampConDetalles(id);
    }
    // Nuevo endpoint para listar bootcamps con detalles (capacidades y tecnologías)
    @GetMapping
    public Flux<BootcampDetailsDTO> listarBootcampsConDetalles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nombre") String sortField,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        return bootcampService.listarBootcampsConDetalles(page, size, sortField, sortDirection);
    }
}
