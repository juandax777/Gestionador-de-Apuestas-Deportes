package com.uc.ApuestasDeportivas.Controladores;

import com.uc.ApuestasDeportivas.Persistencia.Entidades.Partido;
import com.uc.ApuestasDeportivas.Servicios.PartidoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PartidoControlador {

    @Autowired
    private PartidoServicio partidoServicio;

    @GetMapping("/partidos/liga")
    public String verPartidosPorLiga(@RequestParam(value = "liga", required = false) String liga, Model model) {
        if (liga == null || liga.isBlank()) {
            model.addAttribute("error", "Liga no especificada.");
            return "error";
        }

        List<List<Partido>> jornadas = partidoServicio.generarPartidosPorJornadas(liga);
        model.addAttribute("liga", liga);
        model.addAttribute("jornadas", jornadas);
        return "partidosPorLiga";
    }
}
