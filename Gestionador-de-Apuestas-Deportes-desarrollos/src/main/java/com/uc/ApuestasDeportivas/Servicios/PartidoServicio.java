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

        if (equipos.size() < 2) {
            throw new IllegalArgumentException("La liga debe tener al menos dos equipos.");
        }

        int numeroDeJornadas = equipos.size() - 1; // Total de jornadas: n-1 para n equipos
        int partidosPorJornada = equipos.size() / 2;

        List<List<Partido>> todasLasJornadas = new ArrayList<>();
        Set<String> enfrentamientosPrevios = new HashSet<>();

        // Crear una lista mutable para rotar equipos sin afectar la original
        List<Equipo> equiposRotados = new ArrayList<>(equipos);

        for (int jornada = 0; jornada < numeroDeJornadas; jornada++) {
            List<Partido> jornadaActual = new ArrayList<>();

            for (int i = 0; i < partidosPorJornada; i++) {
                Equipo local = equiposRotados.get(i);
                Equipo visitante = equiposRotados.get(equiposRotados.size() - 1 - i);

                String enfrentamiento = local.getNombre() + " vs " + visitante.getNombre();
                String enfrentamientoInverso = visitante.getNombre() + " vs " + local.getNombre();

                // Verificar que el enfrentamiento no se haya generado previamente
                if (!enfrentamientosPrevios.contains(enfrentamiento) && !enfrentamientosPrevios.contains(enfrentamientoInverso)) {
                    enfrentamientosPrevios.add(enfrentamiento);

                    Random random = new Random();
                    local.setCuota(Math.round((1.00 + (5.20 - 1.00) * random.nextDouble()) * 100.0) / 100.0);
                    visitante.setCuota(Math.round((1.00 + (5.20 - 1.00) * random.nextDouble()) * 100.0) / 100.0);

                    Partido partido = new Partido(null, local, visitante, "Fecha programada", "");
                    jornadaActual.add(partido);
                }
            }

            // Agregar la jornada solo si tiene partidos
            if (!jornadaActual.isEmpty()) {
                todasLasJornadas.add(jornadaActual);
            }

            // Rotar equipos para la próxima jornada
            Collections.rotate(equiposRotados.subList(1, equiposRotados.size()), 1);
        }

        // Persistir partidos en la base de datos
        todasLasJornadas.forEach(jornada -> jornada.forEach(partidoRepositorio::save));
        return todasLasJornadas;
    }
}
