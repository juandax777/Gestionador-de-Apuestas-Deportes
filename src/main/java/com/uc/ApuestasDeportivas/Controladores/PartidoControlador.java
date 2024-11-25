package com.uc.ApuestasDeportivas.Controladores;

import com.uc.ApuestasDeportivas.Persistencia.Entidades.Partido;
import com.uc.ApuestasDeportivas.Servicios.ApuestaServicio;
import com.uc.ApuestasDeportivas.Servicios.PartidoServicio;
import com.uc.ApuestasDeportivas.Servicios.UsuarioServicios;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@AllArgsConstructor
@Controller
public class PartidoControlador {

    private final PartidoServicio partidoServicio;
    private final ApuestaServicio apuestaServicio;
    private final UsuarioServicios usuarioServicios;

    @GetMapping("/partidos/liga")
    public String verPartidosPorLiga(@RequestParam("liga") String liga,
                                     @RequestParam("usuario") String nombreUsuario,
                                     Model model) {
        if (liga == null || liga.isBlank()) {
            model.addAttribute("error", "Liga no especificada.");
            return "error";
        }

        List<List<Partido>> jornadas = partidoServicio.generarPartidosPorJornadas(liga);
        model.addAttribute("liga", liga);
        model.addAttribute("jornadas", jornadas);
        model.addAttribute("usuario", nombreUsuario);
        return "partidosPorLiga";
    }
    @GetMapping("/actualizarPartidos")
    public String actualizarPartidos(String usuario, RedirectAttributes redirectAttributes) {
        // Simular los resultados de los partidos y actualizar las apuestas
        partidoServicio.actualizarResultadosYapuestas();

        redirectAttributes.addFlashAttribute("mensaje", "Jornadas actualizadas.");
        return "redirect:/paginaPrincipal?usuario=" + usuario;
    }

}
