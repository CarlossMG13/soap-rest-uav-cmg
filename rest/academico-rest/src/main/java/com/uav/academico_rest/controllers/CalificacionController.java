package com.uav.academico_rest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uav.academico_rest.models.CalificacionModel;
import com.uav.academico_rest.repositories.ICalificacionRepository;

@RestController
@RequestMapping("/api/calificaciones")
@CrossOrigin(origins = "*") // Frontend llama sin bloqueos
public class CalificacionController {

    @Autowired
    private ICalificacionRepository repository;

    // 1. Guardar nueva calificación (POST)
    @PostMapping
    public CalificacionModel registrarCalificacion(@RequestBody CalificacionModel nuevaCalificacion) {
        return repository.save(nuevaCalificacion);
    }

    // 2. Obtener todas las calificaciones (GET)
    @GetMapping
    public List<CalificacionModel> obtenerTodas() {
        return repository.findAll();
    }

    // 3. Obtener calificaciones de un alumno específico (GET)
    @GetMapping("/alumno/{matricula}")
    public List<CalificacionModel> obtenerPorAlumno(@PathVariable String matricula) {
        return repository.findByMatriculaAlumno(matricula);
    }

}
