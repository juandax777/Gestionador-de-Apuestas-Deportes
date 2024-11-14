package com.uc.ApuestasDeportivas.Servicios;

import com.uc.ApuestasDeportivas.Persistencia.Entidades.Equipo;
import com.uc.ApuestasDeportivas.Persistencia.Entidades.Partido;
import com.uc.ApuestasDeportivas.Persistencia.Repositorios.EquipoRepositorio;
import com.uc.ApuestasDeportivas.Persistencia.Repositorios.PartidoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.HashSet;

@Service
public class PartidoServicio {

    @Autowired
    private EquipoRepositorio equipoRepositorio;

    @Autowired
    private PartidoRepositorio partidoRepositorio;

    public List<List<Partido>> generarPartidosPorJornadas(String liga) {
        List<Equipo> equipos = equipoRepositorio.findByLiga(liga);
        int numeroDeJornadas = equipos.size() - 1;  // Total de jornadas: n-1 para n equipos
        int partidosPorJornada = equipos.size() / 2;
        Random random = new Random();

        List<List<Partido>> todasLasJornadas = new ArrayList<>();
        Set<String> enfrentamientosPrevios = new HashSet<>();

        // Rotar la lista de equipos para cada jornada para asegurar todos contra todos
        for (int j = 0; j < numeroDeJornadas; j++) {
            List<Partido> jornadaActual = new ArrayList<>();
            Collections.rotate(equipos, 1);  // Rotar los equipos para la jornada

            for (int i = 0; i < partidosPorJornada; i++) {
                int indiceLocal = i;
                int indiceVisitante = equipos.size() - 1 - i;

                Equipo local = equipos.get(indiceLocal);
                Equipo visitante = equipos.get(indiceVisitante);

                String enfrentamiento = local.getNombre() + " vs " + visitante.getNombre();
                String enfrentamientoInverso = visitante.getNombre() + " vs " + local.getNombre();

                if (!enfrentamientosPrevios.contains(enfrentamiento) && !enfrentamientosPrevios.contains(enfrentamientoInverso) && local != visitante) {
                    enfrentamientosPrevios.add(enfrentamiento);

                    local.setCuota(1.00 + (5.00 - 1.00) * random.nextDouble());
                    visitante.setCuota(1.00 + (5.00 - 1.00) * random.nextDouble());

                    Partido partido = new Partido(null, local, visitante, "Fecha programada", "");
                    jornadaActual.add(partido);
                }
            }
            todasLasJornadas.add(jornadaActual);
        }

        // Guardar todos los partidos generados en la base de datos
        todasLasJornadas.forEach(jornada -> jornada.forEach(partido -> {
            equipoRepositorio.save(partido.getEquipoLocal());
            equipoRepositorio.save(partido.getEquipoVisitante());
            partidoRepositorio.save(partido);
        }));

        return todasLasJornadas;
    }
}
