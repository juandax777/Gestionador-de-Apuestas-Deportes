package com.uc.ApuestasDeportivas.Persistencia.Repositorios;

import com.uc.ApuestasDeportivas.Persistencia.Entidades.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipoRepositorio extends JpaRepository<Equipo, Long> {
    List<Equipo> findByLiga(String liga);
}
