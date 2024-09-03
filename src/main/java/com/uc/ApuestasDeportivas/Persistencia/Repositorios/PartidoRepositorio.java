package com.uc.ApuestasDeportivas.Persistencia.Repositorios;

import com.uc.ApuestasDeportivas.Persistencia.Entidades.Partido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import java.util.List;

@Repository
public interface PartidoRepositorio extends JpaRepository<Partido, Long> {
    List<Partido> findByEquipoLocalLiga(String liga);
    Optional<Partido> findByEquipoLocalNombreAndEquipoVisitanteNombreAndEquipoLocalLiga(String equipoLocal, String equipoVisitante, String liga);

}
