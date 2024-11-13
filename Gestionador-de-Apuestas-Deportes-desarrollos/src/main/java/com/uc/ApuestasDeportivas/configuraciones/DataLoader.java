package com.uc.ApuestasDeportivas.configuraciones;

import com.uc.ApuestasDeportivas.Persistencia.Entidades.Equipo;
import com.uc.ApuestasDeportivas.Servicios.EquipoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private EquipoServicio equipoServicio;

    @Override
    public void run(String... args) throws Exception {
        // Equipos de la liga escocesa
        List<Equipo> equiposEscocesa = List.of(
                new Equipo(null, "Celtic", "Liga Escocesa"),
                new Equipo(null, "Aberdeen", "Liga Escocesa"),
                new Equipo(null, "Rangers", "Liga Escocesa"),
                new Equipo(null, "Dundee United", "Liga Escocesa"),
                new Equipo(null, "Motherwell", "Liga Escocesa"),
                new Equipo(null, "St Mirren", "Liga Escocesa"),
                new Equipo(null, "Dundee", "Liga Escocesa"),
                new Equipo(null, "Kilmarnock", "Liga Escocesa"),
                new Equipo(null, "Ross County", "Liga Escocesa"),
                new Equipo(null, "St Johnstone", "Liga Escocesa"),
                new Equipo(null, "Heart of Midlothian", "Liga Escocesa"),
                new Equipo(null, "Hiberni", "Liga Escocesa")
        );

        // Equipos de la liga ecuatoriana
        List<Equipo> equiposEcuatoriana = List.of(
                new Equipo(null, "Liga de Quito", "Liga Ecuatoriana"),
                new Equipo(null, "Independiente del Valle", "Liga Ecuatoriana"),
                new Equipo(null, "Barcelona SC", "Liga Ecuatoriana"),
                new Equipo(null, "Universidad Católica (Quito)", "Liga Ecuatoriana"),
                new Equipo(null, "Orense", "Liga Ecuatoriana"),
                new Equipo(null, "Técnico Universitario", "Liga Ecuatoriana"),
                new Equipo(null, "Mushuc Runa", "Liga Ecuatoriana"),
                new Equipo(null, "Libertad (Ecuador)", "Liga Ecuatoriana"),
                new Equipo(null, "Delfín", "Liga Ecuatoriana"),
                new Equipo(null, "Macará", "Liga Ecuatoriana"),
                new Equipo(null, "El Nacional", "Liga Ecuatoriana"),
                new Equipo(null, "Deportivo Cuenca", "Liga Ecuatoriana"),
                new Equipo(null, "Aucas", "Liga Ecuatoriana"),
                new Equipo(null, "Imbabura", "Liga Ecuatoriana"),
                new Equipo(null, "Cumbayá", "Liga Ecuatoriana"),
                new Equipo(null, "Emelec", "Liga Ecuatoriana")
        );

        // Equipos de las eliminatorias sudamericanas
        List<Equipo> equiposSudamericanos = List.of(
                new Equipo(null, "Argentina", "Eliminatorias Sudamericanas"),
                new Equipo(null, "Colombia", "Eliminatorias Sudamericanas"),
                new Equipo(null, "Uruguay", "Eliminatorias Sudamericanas"),
                new Equipo(null, "Brasil", "Eliminatorias Sudamericanas"),
                new Equipo(null, "Ecuador", "Eliminatorias Sudamericanas"),
                new Equipo(null, "Paraguay", "Eliminatorias Sudamericanas"),
                new Equipo(null, "Bolivia", "Eliminatorias Sudamericanas"),
                new Equipo(null, "Venezuela", "Eliminatorias Sudamericanas"),
                new Equipo(null, "Perú", "Eliminatorias Sudamericanas"),
                new Equipo(null, "Chile", "Eliminatorias Sudamericanas")
        );

        // Guardar los equipos en la base de datos
        equipoServicio.guardarEquipos(equiposEscocesa);
        equipoServicio.guardarEquipos(equiposEcuatoriana);
        equipoServicio.guardarEquipos(equiposSudamericanos);
    }
}
