package com.uc.ApuestasDeportivas.Controladores;

import com.uc.ApuestasDeportivas.Persistencia.Entidades.Equipo;
import com.uc.ApuestasDeportivas.Servicios.EquipoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class EquipoControlador {

    @Autowired
    private EquipoServicio equipoServicio;

    @GetMapping("/equipos/liga")
    public String verEquiposPorLiga(@RequestParam("liga") String liga, Model model) {
        List<Equipo> equipos = equipoServicio.obtenerEquiposPorLiga(liga);
        model.addAttribute("liga", liga);
        model.addAttribute("equipos", equipos);
        return "equiposPorLiga"; // Nombre del HTML
    }
}
