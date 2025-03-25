package com.reto_reactivo.bootcamps.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BootcampDetailsDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private List<CapacidadFullDTO> capacidades;
}
