package com.uc.ApuestasDeportivas.Servicios;

import com.uc.ApuestasDeportivas.Persistencia.Entidades.EventoDeportivo;
import com.uc.ApuestasDeportivas.Persistencia.Repositorios.EventoDeportivoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventoDeportivoServicio {

    @Autowired
    private EventoDeportivoRepositorio repositorio;

    // Obtener lista de ligas disponibles
    public List<String> obtenerLigas() {
        List<String> ligas = repositorio.findAll()
                .stream()
                .map(EventoDeportivo::getLiga)
                .distinct()
                .collect(Collectors.toList());

        // Si no hay ligas en la base de datos, devolver las ligas estáticas
        if (ligas.isEmpty()) {
            ligas = List.of("Liga Escocesa", "Liga Ecuatoriana", "Eliminatorias Sudamericanas");
        }
        return ligas;
    }

    // Obtener eventos por liga
    public List<EventoDeportivo> obtenerEventosPorLiga(String liga) {
        return repositorio.findByLiga(liga);
    }
}
