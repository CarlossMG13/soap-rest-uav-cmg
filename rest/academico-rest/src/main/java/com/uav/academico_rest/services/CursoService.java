package com.uav.academico_rest.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uav.academico_rest.models.CursoModel;
import com.uav.academico_rest.repositories.ICursoRepository;

@Service
public class CursoService {

    @Autowired
    private ICursoRepository cursoRepository;

    // Guardar
    public CursoModel registrarCurso (CursoModel curso) {
        return cursoRepository.save(curso);
    }

    // Listar todos
    public List<CursoModel> listarTodos() {
        return cursoRepository.findAll();
    }

    // Buscar por ID
    public Optional<CursoModel> buscarPorId(Long id) {
        return cursoRepository.findById(id);
    }

    // Eliminar por ID
    public boolean eliminarPorId(Long id) {
        if (cursoRepository.existsById(id)) {
            cursoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Eliminar por Nombre o Fecha Inicio
    public String eliminarPorFiltro(String nombre, String fecha) {
        if (nombre != null && !nombre.isEmpty()) {
            cursoRepository.deleteByNombre(nombre);
            return "Cursos con nombre '" + nombre + "' eliminados.";
        } 
        else if (fecha != null && !fecha.isEmpty()) {
            cursoRepository.deleteByFechaInicio(fecha);
            return "Cursos con fecha de inicio '" + fecha + "' eliminados.";
        } 
        else {
            return null; // Parametro invalido
        }
    }
    
}
