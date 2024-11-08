package com.uc.ApuestasDeportivas.Servicios;

import com.uc.ApuestasDeportivas.Persistencia.Entidades.EventoDeportivo;
import com.uc.ApuestasDeportivas.Persistencia.Repositorios.EventoDeportivoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class EventoDeportivoServicio {

    @Autowired
    private EventoDeportivoRepositorio repositorio;

    public List<EventoDeportivo> obtenerEventosFutbol() {
        List<String> competencias = Arrays.asList("Liga Escocesa", "Liga Ecuatoriana", "Eliminatorias Sudamérica");
        return repositorio.findByNombreIn(competencias);
    }
}
