package com.uc.ApuestasDeportivas.Persistencia.Repositorios;

import com.uc.ApuestasDeportivas.Persistencia.Entidades.EventoDeportivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventoDeportivoRepositorio extends JpaRepository<EventoDeportivo, Long> {
    List<EventoDeportivo> findByLiga(String liga); // Busca eventos por liga
}
