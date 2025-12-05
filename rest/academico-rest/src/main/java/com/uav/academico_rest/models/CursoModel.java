package com.uav.academico_rest.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cursos")
public class CursoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;
    
    @Column(name = "profesor", nullable = false, length = 100)
    private String profesor;

    @Column(name = "fecha_inicio", nullable = false)
    private String fechaInicio; // "2024-01-20"

    @Column(name = "fecha_fin")
    private String fechaFin;

    @Column(name = "horario", length = 50)
    private String horario;

    @Column(name = "creditos")
    private Integer creditos;

    @Column(name = "cupo_maximo") 
    private Integer cupoMaximo;

    // ------------- Constructores

    // Vacio
    public CursoModel(){}

    // Completo
    public CursoModel(String nombre, String profesor, String fechaInicio, Integer creditos){
        this.nombre = nombre;
        this.profesor = profesor;
        this.fechaInicio = fechaInicio;
        this.creditos = creditos;
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

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public Integer getCreditos() {
        return creditos;
    }

    public void setCreditos(Integer creditos) {
        this.creditos = creditos;
    }

    public Integer getCupoMaximo() {
        return cupoMaximo;
    }

    public void setCupoMaximo(Integer cupoMaximo) {
        this.cupoMaximo = cupoMaximo;
    }

    // ------------- Getters y Setters

    

}
