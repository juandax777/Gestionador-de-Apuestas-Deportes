package com.uc.ApuestasDeportivas.Controladores;

import com.uc.ApuestasDeportivas.Persistencia.Entidades.EventoDeportivo;
import com.uc.ApuestasDeportivas.Servicios.EventoDeportivoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class EventoDeportivoControlador {

    @Autowired
    private EventoDeportivoServicio servicio;

    private double saldo = 90000.0; // Saldo inicial fijo para pruebas

    // Mostrar ligas disponibles
    @GetMapping("/eventos")
    public String verLigas(Model model) {
        List<String> ligas = servicio.obtenerLigas(); // Obtener ligas
        model.addAttribute("ligas", ligas);
        model.addAttribute("saldo", saldo); // Pasar saldo al modelo
        return "ligas";
    }

    // Mostrar eventos por liga
    @GetMapping("/eventos/liga")
    public String verEventosPorLiga(@RequestParam("liga") String liga, Model model) {
        List<EventoDeportivo> eventos = servicio.obtenerEventosPorLiga(liga); // Obtener eventos por liga
        model.addAttribute("liga", liga);
        model.addAttribute("eventos", eventos);
        model.addAttribute("saldo", saldo); // Pasar saldo al modelo
        return "eventosPorLiga";
    }

    // Método para actualizar el saldo (simulación de una transacción)
    public void actualizarSaldo(double monto) {
        saldo += monto;
    }
}