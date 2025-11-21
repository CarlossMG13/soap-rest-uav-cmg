package com.uav.academico_rest.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uav.academico_rest.models.CalificacionModel;

public interface ICalificacionRepository extends JpaRepository<CalificacionModel, Long> {
    List<CalificacionModel> findByMatriculaAlumno(String matriculaAlumno);
}
