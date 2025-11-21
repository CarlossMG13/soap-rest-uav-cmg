package com.uav.academico_rest.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table (name = "calificaciones")
public class CalificacionModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String matriculaAlumno;

    @Column(nullable = false)
    private String materia;

    @Column(nullable = false)
    private Double calificacion;

    @Column(updatable = false)
    private LocalDateTime fechaRegistro;

    // Constructor vacío
    public CalificacionModel() {}

    // Constructor
    public CalificacionModel(String matriculaAlumno, String materia, Double calificacion) {
        this.matriculaAlumno = matriculaAlumno;
        this.materia = materia;
        this.calificacion = calificacion;
    }

    // Antes de guardar, asignamos la fecha actual automáticamente
    @PrePersist
    protected void onCreate() {
        fechaRegistro = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatriculaAlumno() {
        return matriculaAlumno;
    }

    public void setMatriculaAlumno(String matriculaAlumno) {
        this.matriculaAlumno = matriculaAlumno;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public Double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Double calificacion) {
        this.calificacion = calificacion;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    

}
