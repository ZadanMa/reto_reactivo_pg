// src/main/java/com/reto_reactivo/tecnologia/adapters/in/webflux/TecnologiaController.java
package com.reto_reactivo.tecnologia.adapters.in.webflux;

import com.reto_reactivo.tecnologia.application.service.TecnologiaService;
import com.reto_reactivo.tecnologia.domain.model.Tecnologia;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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
    public Mono<Tecnologia> registrarTecnologia(@Valid @RequestBody Tecnologia tecnologia) {
        return tecnologiaService.registrarTecnologia(tecnologia);
    }
}
