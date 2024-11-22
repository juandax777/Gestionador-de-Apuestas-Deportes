package com.uc.ApuestasDeportivas.Controladores;

import com.uc.ApuestasDeportivas.Persistencia.Entidades.Apuesta;
import com.uc.ApuestasDeportivas.Servicios.ApuestaServicio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@AllArgsConstructor
@Controller
public class ApuestaControlador {

    private final ApuestaServicio apuestaServicio;

    @GetMapping("/apuestas")
    public String listarApuestas(Model model) {
        List<Apuesta> apuestas = apuestaServicio.listarTodas();
        model.addAttribute("apuestas", apuestas);
        return "historialApuestas"; // Vista de historial de apuestas
    }

    @PostMapping("/resumenApuesta")
    public String mostrarResumen(@RequestParam("tipoCuota") String tipoCuota,
                                 @RequestParam("cuotaSeleccionada") double cuotaSeleccionada,
                                 @RequestParam("equipoLocal") String equipoLocal,
                                 @RequestParam("equipoVisitante") String equipoVisitante,
                                 @RequestParam("liga") String liga,
                                 Model model) {
        // Pasar los datos al modelo para mostrarlos en la vista
        model.addAttribute("tipoCuota", tipoCuota);
        model.addAttribute("cuotaSeleccionada", cuotaSeleccionada);
        model.addAttribute("equipoLocal", equipoLocal);
        model.addAttribute("equipoVisitante", equipoVisitante);
        model.addAttribute("liga", liga);
        return "resumenApuesta"; // Vista del resumen de apuesta
    }

    @PostMapping("/confirmarApuesta")
    public String registrarApuesta(@RequestParam("tipoCuota") String tipoCuota,
                                   @RequestParam("cuotaSeleccionada") double cuotaSeleccionada,
                                   @RequestParam("montoApostado") double montoApostado,
                                   @RequestParam("equipoLocal") String equipoLocal,
                                   @RequestParam("equipoVisitante") String equipoVisitante,
                                   @RequestParam("liga") String liga,
                                   Model model) {
        // Simular saldo (para pruebas)
        double saldo = 100000.00; // Cambiar esto para que sea dinámico (por ejemplo, obtenido de la base de datos)

        if (saldo < montoApostado) {
            // Si el saldo es insuficiente, se devuelve al resumen con un mensaje de error
            model.addAttribute("error", "Saldo insuficiente para realizar la apuesta.");
            model.addAttribute("tipoCuota", tipoCuota);
            model.addAttribute("cuotaSeleccionada", cuotaSeleccionada);
            model.addAttribute("equipoLocal", equipoLocal);
            model.addAttribute("equipoVisitante", equipoVisitante);
            model.addAttribute("liga", liga);
            model.addAttribute("saldo", saldo);
            return "resumenApuesta"; // Vista del resumen con el error
        }

        // Crear y guardar la apuesta
        Apuesta nuevaApuesta = new Apuesta();
        nuevaApuesta.setTipoCuota(tipoCuota);
        nuevaApuesta.setCuotaSeleccionada(cuotaSeleccionada);
        nuevaApuesta.setMontoApostado(montoApostado);
        nuevaApuesta.setEquipoLocal(equipoLocal);
        nuevaApuesta.setEquipoVisitante(equipoVisitante);
        nuevaApuesta.setEstado("Pendiente");

        apuestaServicio.registrarApuesta(nuevaApuesta);

        // Descontar el saldo
        saldo -= montoApostado;
        model.addAttribute("saldo", saldo);

        // Redirigir al historial de apuestas
        return "redirect:/apuestas";
    }

    @PostMapping("/cancelarApuesta")
    public String cancelarApuesta(@RequestParam("idApuesta") Long idApuesta, Model model) {
        // Obtener la apuesta a cancelar
        Apuesta apuesta = apuestaServicio.obtenerPorId(idApuesta);

        if (apuesta != null && "Pendiente".equals(apuesta.getEstado())) {
            // Cambiar el estado de la apuesta a "Cancelada"
            apuesta.setEstado("Cancelada");
            apuestaServicio.actualizarApuesta(apuesta);
            model.addAttribute("mensaje", "Apuesta cancelada exitosamente.");
        } else {
            model.addAttribute("error", "No se pudo cancelar la apuesta.");
        }

        return "redirect:/apuestas"; // Redirigir al historial de apuestas
    }
}
