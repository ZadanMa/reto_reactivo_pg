package com.reto_reactivo.bootcamps.domain.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;
import java.util.Set;

@Table("bootcamps")
public class Bootcamp {

    @Id
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres")
    private String nombre;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 90, message = "La descripción no puede tener más de 90 caracteres")
    private String descripcion;

    @NotEmpty(message = "Debe tener al menos 1 capacidad asociada")
    @Size(min = 1, max = 4, message = "Un bootcamp debe tener entre 1 y 4 capacidades")
    private List<Long> capacidadIds;

    public Bootcamp() {}

    public Bootcamp(Long id, String nombre, String descripcion, List<Long> capacidadIds) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.capacidadIds = capacidadIds;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public List<Long> getCapacidadIds() {
        return capacidadIds;
    }
    public void setCapacidadIds(List<Long> capacidadIds) {
        this.capacidadIds = capacidadIds;
    }
}
