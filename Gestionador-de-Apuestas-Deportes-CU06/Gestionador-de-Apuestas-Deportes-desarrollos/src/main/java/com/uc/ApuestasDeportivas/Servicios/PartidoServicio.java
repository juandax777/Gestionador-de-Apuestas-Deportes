package com.uc.ApuestasDeportivas.Servicios;

import com.uc.ApuestasDeportivas.Persistencia.Entidades.Equipo;
import com.uc.ApuestasDeportivas.Persistencia.Entidades.Partido;
import com.uc.ApuestasDeportivas.Persistencia.Repositorios.EquipoRepositorio;
import com.uc.ApuestasDeportivas.Persistencia.Repositorios.PartidoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@Service
public class PartidoServicio {

    @Autowired
    private EquipoRepositorio equipoRepositorio;

    @Autowired
    private PartidoRepositorio partidoRepositorio;

    public List<Partido> generarPartidos(String liga) {
        List<Equipo> equipos = equipoRepositorio.findByLiga(liga);
        Collections.shuffle(equipos); // Mezcla los equipos al azar
        Random random = new Random();

        // Crea enfrentamientos por pares
        IntStream.range(0, equipos.size() / 2).forEach(i -> {
            Equipo local = equipos.get(i * 2);
            Equipo visitante = equipos.get(i * 2 + 1);

            local.setCuota(1.00 + (5.25 - 1.00) * random.nextDouble()); // Cuota al azar
            visitante.setCuota(1.00 + (5.25 - 1.00) * random.nextDouble()); // Cuota al azar

            equipoRepositorio.save(local);
            equipoRepositorio.save(visitante);

            Partido partido = new Partido(
                    null,
                    local,
                    visitante,
                    "2024-11-12", // Fecha fija o dinámica
                    "" // Resultado vacío por ahora
            );

            partidoRepositorio.save(partido);
        });

        return partidoRepositorio.findByEquipoLocalLiga(liga);
    }
}
