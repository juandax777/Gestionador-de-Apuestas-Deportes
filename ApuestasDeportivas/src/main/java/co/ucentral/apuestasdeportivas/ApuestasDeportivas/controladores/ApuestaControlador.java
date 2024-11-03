package co.ucentral.apuestasdeportivas.ApuestasDeportivas.controladores;

import co.ucentral.apuestasdeportivas.ApuestasDeportivas.persistencia.entidades.Apuesta;
import co.ucentral.apuestasdeportivas.ApuestasDeportivas.servicios.ApuestaServicio;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApuestaControlador {

    private final ApuestaServicio apuestaServicio;

    // Inyección de dependencias a través del constructor
    public ApuestaControlador(ApuestaServicio apuestaServicio) {
        this.apuestaServicio = apuestaServicio;
    }

    // Manejo de la solicitud GET para listar todas las apuestas
    @GetMapping("/apuestas")
    public String listarApuestas(Model model) {
        model.addAttribute("apuestas", apuestaServicio.listarTodas());
        return "gestionApuestas"; // Nombre de la vista (gestionApuestas.html)
    }
}