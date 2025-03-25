package com.reto_reactivo.bootcamps.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CapacidadFullDTO {
    private Long id;
    private String nombre;
    private List<TecnologiaMinDTO> tecnologias;
}
