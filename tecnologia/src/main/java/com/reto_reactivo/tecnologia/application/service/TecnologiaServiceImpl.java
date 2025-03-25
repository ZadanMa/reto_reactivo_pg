// src/main/java/com/reto_reactivo/tecnologia/application/service/TecnologiaServiceImpl.java
package com.reto_reactivo.tecnologia.application.service;

import com.reto_reactivo.tecnologia.domain.exception.DuplicateTechnologyNameException;
import com.reto_reactivo.tecnologia.domain.model.Tecnologia;
import com.reto_reactivo.tecnologia.domain.port.out.TecnologiaRepository;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.stereotype.Service;

@Service
public class TecnologiaServiceImpl implements TecnologiaService {

    private final TecnologiaRepository tecnologiaRepository;

    public TecnologiaServiceImpl(TecnologiaRepository tecnologiaRepository) {
        this.tecnologiaRepository = tecnologiaRepository;
    }

    @Override
    public Mono<Tecnologia> registrarTecnologia(Tecnologia tecnologia) {
        return tecnologiaRepository.existsByNombre(tecnologia.getNombre())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new DuplicateTechnologyNameException("El nombre de la tecnología ya existe"));
                    }
                    return tecnologiaRepository.save(tecnologia);
                });
    }
    @Override
    public Flux<Tecnologia> listarTecnologias(int page, int size, String sortDirection) {
        // Construir el objeto Sort según la dirección (ascendente o descendente)
        Sort sort = sortDirection.equalsIgnoreCase("asc") ?
                Sort.by("nombre").ascending() :
                Sort.by("nombre").descending();

        // Aplicar paginación: calcular offset y limitar el número de elementos
        return tecnologiaRepository.findAllTecnologias(sort)
                .skip((long) page * size)
                .take(size);
    }
}
