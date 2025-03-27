// src/main/java/com/reto_reactivo/capacidad/domain/model/Capacidad.java
package com.reto_reactivo.capacidad.domain.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;
import java.util.Set;

@Table("capacidades")
public class Capacidad {

    @Id
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres")
    private String nombre;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 90, message = "La descripción no puede tener más de 90 caracteres")
    private String descripcion;

    @NotEmpty(message = "Se debe asociar al menos 3 tecnologias")
    @Size(min = 3, max = 20, message = "La capacidad debe tener entre 3 y 20 tecnologias")
    @Column("tecnologia_ids")
    private List<Long> tecnologiaIds;

    public Capacidad() {
    }

    public Capacidad(Long id, String nombre, String descripcion, List<Long> tecnologiaIds) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tecnologiaIds = tecnologiaIds;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public List<Long> getTecnologiaIds() { return tecnologiaIds; }
    public void setTecnologiaIds(List<Long> tecnologiaIds) { this.tecnologiaIds = tecnologiaIds; }
}
