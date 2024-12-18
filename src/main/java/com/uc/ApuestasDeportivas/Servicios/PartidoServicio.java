package com.uc.ApuestasDeportivas.Servicios;

import com.uc.ApuestasDeportivas.Persistencia.Entidades.Equipo;
import com.uc.ApuestasDeportivas.Persistencia.Entidades.Partido;
import com.uc.ApuestasDeportivas.Persistencia.Repositorios.EquipoRepositorio;
import com.uc.ApuestasDeportivas.Persistencia.Repositorios.PartidoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PartidoServicio {

    @Autowired
    private EquipoRepositorio equipoRepositorio;

    @Autowired
    private PartidoRepositorio partidoRepositorio;

    public List<List<Partido>> generarPartidosPorJornadas(String liga) {
        // Verificar si ya existen partidos para esta liga
        List<Partido> partidosExistentes = partidoRepositorio.findByEquipoLocalLiga(liga);
        if (!partidosExistentes.isEmpty()) {
            // Si ya existen partidos, agruparlos por jornadas y devolverlos
            return agruparPorJornadas(partidosExistentes);
        }

        // Si no existen partidos, generarlos
        List<Equipo> equipos = equipoRepositorio.findByLiga(liga);
        if (equipos.size() < 2) {
            throw new IllegalArgumentException("La liga debe tener al menos dos equipos.");
        }

        int numeroDeJornadas;
        int partidosPorJornada;

        // Configuración específica según la liga
        switch (liga) {
            case "Liga Escocesa":
                numeroDeJornadas = 11;
                partidosPorJornada = 9;
                break;
            case "Eliminatorias Sudamericanas":
                numeroDeJornadas = 9;
                partidosPorJornada = 5;
                break;
            case "Liga Ecuatoriana":
                numeroDeJornadas = 15;
                partidosPorJornada = 8;
                break;
            default:
                throw new IllegalArgumentException("Liga no reconocida: " + liga);
        }

        List<List<Partido>> todasLasJornadas = new ArrayList<>();
        Set<String> enfrentamientosPrevios = new HashSet<>();
        List<Equipo> equiposRotados = new ArrayList<>(equipos);
        Random random = new Random();

        for (int jornada = 0; jornada < numeroDeJornadas; jornada++) {
            List<Partido> jornadaActual = new ArrayList<>();
            String fechaJornada = "Jornada " + (jornada + 1); // Fecha o identificador único para la jornada

            for (int i = 0; i < partidosPorJornada; i++) {
                Equipo local = equiposRotados.get(i);
                Equipo visitante = equiposRotados.get(equiposRotados.size() - 1 - i);

                String enfrentamiento = local.getNombre() + " vs " + visitante.getNombre();
                String enfrentamientoInverso = visitante.getNombre() + " vs " + local.getNombre();

                // Verificar que el enfrentamiento no se haya generado previamente
                if (!enfrentamientosPrevios.contains(enfrentamiento) && !enfrentamientosPrevios.contains(enfrentamientoInverso)) {
                    enfrentamientosPrevios.add(enfrentamiento);

                    // Generar cuotas aleatorias
                    local.setCuota(Math.round((1.00 + (5.20 - 1.00) * random.nextDouble()) * 100.0) / 100.0);
                    visitante.setCuota(Math.round((1.00 + (5.20 - 1.00) * random.nextDouble()) * 100.0) / 100.0);
                    Double cuotaEmpate = Math.round((1.50 + (3.50 - 1.50) * random.nextDouble()) * 100.0) / 100.0;

                    // Generar resultados aleatorios
                    int golesLocal = random.nextInt(10); // Goles aleatorios entre 0 y 9
                    int golesVisitante = random.nextInt(10);

                    Partido partido = new Partido(
                            null,
                            local,
                            visitante,
                            fechaJornada,
                            golesLocal + "-" + golesVisitante, // Formato de resultado
                            cuotaEmpate,
                            golesLocal,
                            golesVisitante
                    );
                    jornadaActual.add(partido);
                }
            }

            if (!jornadaActual.isEmpty()) {
                todasLasJornadas.add(jornadaActual);
            }

            Collections.rotate(equiposRotados.subList(1, equiposRotados.size()), 1);
        }

        // Persistir los partidos en la base de datos
        todasLasJornadas.forEach(partidoRepositorio::saveAll);
        return todasLasJornadas;
    }

    private List<List<Partido>> agruparPorJornadas(List<Partido> partidos) {
        Map<String, List<Partido>> partidosAgrupados = partidos.stream()
                .collect(Collectors.groupingBy(Partido::getFecha)); // Agrupa por la "fecha" de la jornada
        return new ArrayList<>(partidosAgrupados.values());
    }

    public Partido obtenerPartidoPorId(Long id) {
        return partidoRepositorio.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Partido no encontrado: " + id));
    }
    public Partido obtenerPartidoPorEquiposYLiga(String equipoLocal, String equipoVisitante, String liga) {
        return partidoRepositorio.findByEquipoLocalNombreAndEquipoVisitanteNombreAndEquipoLocalLiga(equipoLocal, equipoVisitante, liga)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró un partido con los equipos y la liga especificados."));
    }
}