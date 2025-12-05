package com.uav.academico_rest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uav.academico_rest.models.CursoModel;

import jakarta.transaction.Transactional;

@Repository
public interface ICursoRepository extends JpaRepository<CursoModel, Long> {
    @Transactional
    void deleteByNombre(String nombre);

    @Transactional
    void deleteByFechaInicio(String fechaInicio);
}
