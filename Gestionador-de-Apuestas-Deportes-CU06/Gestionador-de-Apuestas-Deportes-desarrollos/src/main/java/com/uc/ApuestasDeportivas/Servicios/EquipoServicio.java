package com.uc.ApuestasDeportivas.Servicios;

import com.uc.ApuestasDeportivas.Persistencia.Entidades.Equipo;
import com.uc.ApuestasDeportivas.Persistencia.Repositorios.EquipoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipoServicio {

    @Autowired
    private EquipoRepositorio equipoRepositorio;

    public List<Equipo> obtenerEquiposPorLiga(String liga) {
        return equipoRepositorio.findByLiga(liga);
    }

    public void guardarEquipos(List<Equipo> equipos) {
        equipoRepositorio.saveAll(equipos);
    }
}
