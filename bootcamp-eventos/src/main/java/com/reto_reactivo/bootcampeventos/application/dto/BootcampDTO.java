package com.reto_reactivo.bootcampeventos.application.dto;

import java.util.List;
import java.util.Set;


public class BootcampDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private List<Long> capacidadIds;

    public BootcampDTO() {
    }

    public BootcampDTO(String nombre, Long id, String descripcion, List<Long> capacidadIds) {
        this.nombre = nombre;
        this.id = id;
        this.descripcion = descripcion;
        this.capacidadIds = capacidadIds;
    }

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
