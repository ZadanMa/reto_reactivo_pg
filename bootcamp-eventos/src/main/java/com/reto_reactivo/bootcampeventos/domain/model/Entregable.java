package com.reto_reactivo.bootcampeventos.domain.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Table("entregables")
public class Entregable {

    @Id
    private Long id;

    @NotNull(message = "El id de la iteración es obligatorio")
    private Long iteracionId;

    @NotNull(message = "El tutorId es obligatorio")
    private Long tutorId; // Identificador del tutor que crea el entregable

    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    private String descripcion;

    @NotNull(message = "La fecha límite es obligatoria")
    private LocalDate fechaLimite;

    // Campos de auditoría (opcional)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Entregable() {}

    public Entregable(Long id, Long iteracionId, Long tutorId, String titulo, String descripcion, LocalDate fechaLimite, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.iteracionId = iteracionId;
        this.tutorId = tutorId;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaLimite = fechaLimite;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters y setters

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getIteracionId() {
        return iteracionId;
    }
    public void setIteracionId(Long iteracionId) {
        this.iteracionId = iteracionId;
    }

    public Long getTutorId() {
        return tutorId;
    }
    public void setTutorId(Long tutorId) {
        this.tutorId = tutorId;
    }

    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaLimite() {
        return fechaLimite;
    }
    public void setFechaLimite(LocalDate fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
