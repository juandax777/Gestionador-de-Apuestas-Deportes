package com.uc.ApuestasDeportivas.Controladores;

import com.uc.ApuestasDeportivas.Persistencia.Entidades.EventoDeportivo;
import com.uc.ApuestasDeportivas.Servicios.EventoDeportivoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class EventoDeportivoControlador {

    @Autowired
    private EventoDeportivoServicio servicio;

    @GetMapping("/eventos")
    public String verEventosDeportivos(Model model) {
        List<EventoDeportivo> eventos = servicio.obtenerEventosFutbol();
        model.addAttribute("eventos", eventos);
        return "eventosDeportivos";
    }
}
