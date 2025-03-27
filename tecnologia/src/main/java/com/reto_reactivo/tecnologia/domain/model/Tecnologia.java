// src/main/java/com/reto_reactivo/tecnologia/domain/model/Tecnologia.java
package com.reto_reactivo.tecnologia.domain.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


import java.util.Objects;

@Table("tecnologias")
public class Tecnologia {

    @Id
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres")
    private String nombre;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 90, message = "La descripción no puede tener más de 90 caracteres")
    private String descripcion;

    public Tecnologia() {
    }

    public Tecnologia(Long id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // Getters y Setters
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Tecnologia that = (Tecnologia) o;
        return Objects.equals(id, that.id) && Objects.equals(nombre, that.nombre) && Objects.equals(descripcion, that.descripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, descripcion);
    }
}
