package com.uc.ApuestasDeportivas.Controladores;

import com.uc.ApuestasDeportivas.Persistencia.Entidades.Apuesta;
import com.uc.ApuestasDeportivas.Servicios.ApuestaServicio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@AllArgsConstructor
@Controller
public class ApuestaControlador {

    private final ApuestaServicio apuestaServicio;

    @GetMapping("/apuestas")
    public String listarApuestas(Model model) {
        model.addAttribute("apuestas", apuestaServicio.listarTodas());
        return "gestionApuestas"; // Nombre de la vista HTML
    }

    @PostMapping("/apuestas")
    public String registrarApuesta(Apuesta apuesta, Model model) {
        apuestaServicio.registrarApuesta(apuesta);
        model.addAttribute("mensaje", "Apuesta registrada exitosamente");
        return "gestionApuestas";
    }
}
