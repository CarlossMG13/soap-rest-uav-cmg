package com.uav.academico_rest.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uav.academico_rest.models.CursoModel;
import com.uav.academico_rest.services.CursoService;

@RestController
@RequestMapping("/api/cursos")
@CrossOrigin(origins = "*") // Acceso desde el Frontend
public class CursoController {
    
    @Autowired
    private CursoService cursoService;

    // Registro (POST)
    @PostMapping
    public ResponseEntity<CursoModel> registrar(@RequestBody CursoModel curso) {
        return ResponseEntity.ok(cursoService.registrarCurso(curso));
    }

    // Listar Todos (GET)
    @GetMapping
    public List<CursoModel> listar() {
        return cursoService.listarTodos();
    }

    // Buscar por ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<CursoModel> obtenerPorId(@PathVariable Long id) {
        Optional<CursoModel> curso = cursoService.buscarPorId(id);
        if (curso.isPresent()) {
            return ResponseEntity.ok(curso.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar por ID (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarId(@PathVariable Long id) {
        if (cursoService.eliminarPorId(id)) {
            return ResponseEntity.ok("Curso eliminado por ID correctamente.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar por Nombre o Fecha Inicio (DELETE)
    // /api/cursos/filtro?nombre=Fisica
    // /api/cursos/filtro?fecha=2024-01-20

    @DeleteMapping("/filtro")
    public ResponseEntity<String> eliminarFiltro(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String fecha) {
        
        String resultado = cursoService.eliminarPorFiltro(nombre, fecha);
        
        if (resultado != null) {
            return ResponseEntity.ok(resultado);
        } else {
            return ResponseEntity.badRequest().body("Error: Debes enviar el parámetro 'nombre' o 'fecha'.");
        }
    }

}
