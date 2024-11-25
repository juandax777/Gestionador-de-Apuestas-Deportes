package com.uc.ApuestasDeportivas.Controladores;

import com.uc.ApuestasDeportivas.Persistencia.Entidades.Apuesta;
import com.uc.ApuestasDeportivas.Persistencia.Entidades.Usuario;
import com.uc.ApuestasDeportivas.Servicios.ApuestaServicio;
import com.uc.ApuestasDeportivas.Servicios.UsuarioServicios;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@AllArgsConstructor
@Controller
public class ApuestaControlador {

    @Autowired
    private ApuestaServicio apuestaServicio;

    @Autowired
    private UsuarioServicios usuarioServicios;

    @GetMapping("/apuestas")
    public String listarApuestas(@RequestParam("usuario") String nombreUsuario, Model model) {
        Usuario usuario = usuarioServicios.obtenerUsuarioPorNombre(nombreUsuario);
        List<Apuesta> apuestas = apuestaServicio.listarPorUsuario(usuario);
        model.addAttribute("apuestas", apuestas);
        model.addAttribute("usuario", nombreUsuario);
        return "historialApuestas"; // Vista de historial de apuestas
    }


    @PostMapping("/resumenApuesta")
    public String mostrarResumen(@RequestParam("tipoCuota") String tipoCuota,
                                 @RequestParam("cuotaSeleccionada") double cuotaSeleccionada,
                                 @RequestParam("equipoLocal") String equipoLocal,
                                 @RequestParam("equipoVisitante") String equipoVisitante,
                                 @RequestParam("liga") String liga,
                                 @RequestParam("usuario") String nombreUsuario,
                                 Model model) {
        // Obtener el saldo del usuario
        double saldo = usuarioServicios.obtenerSaldo(nombreUsuario);
        model.addAttribute("saldo", saldo);
        // Pasar los datos al modelo
        model.addAttribute("tipoCuota", tipoCuota);
        model.addAttribute("cuotaSeleccionada", cuotaSeleccionada);
        model.addAttribute("equipoLocal", equipoLocal);
        model.addAttribute("equipoVisitante", equipoVisitante);
        model.addAttribute("liga", liga);
        model.addAttribute("usuario", nombreUsuario);
        return "resumenApuesta";
    }


    @PostMapping("/confirmarApuesta")
    public String registrarApuesta(@RequestParam("tipoCuota") String tipoCuota,
                                   @RequestParam("cuotaSeleccionada") double cuotaSeleccionada,
                                   @RequestParam("montoApostado") double montoApostado,
                                   @RequestParam("equipoLocal") String equipoLocal,
                                   @RequestParam("equipoVisitante") String equipoVisitante,
                                   @RequestParam("liga") String liga,
                                   @RequestParam("usuario") String nombreUsuario,
                                   Model model) {
        // Obtener el saldo del usuario
        double saldo = usuarioServicios.obtenerSaldo(nombreUsuario);

        if (saldo < montoApostado) {
            // Saldo insuficiente
            model.addAttribute("error", "Saldo insuficiente para realizar la apuesta.");
            model.addAttribute("tipoCuota", tipoCuota);
            model.addAttribute("cuotaSeleccionada", cuotaSeleccionada);
            model.addAttribute("equipoLocal", equipoLocal);
            model.addAttribute("equipoVisitante", equipoVisitante);
            model.addAttribute("liga", liga);
            model.addAttribute("saldo", saldo);
            model.addAttribute("usuario", nombreUsuario);
            return "resumenApuesta";
        }

        // Obtener el objeto Usuario a partir del nombre de usuario
        Usuario usuario = usuarioServicios.obtenerUsuarioPorNombre(nombreUsuario);

        // Crear y guardar la apuesta
        Apuesta nuevaApuesta = new Apuesta();
        nuevaApuesta.setTipoCuota(tipoCuota);
        nuevaApuesta.setCuotaSeleccionada(cuotaSeleccionada);
        nuevaApuesta.setMontoApostado(montoApostado);
        nuevaApuesta.setEquipoLocal(equipoLocal);
        nuevaApuesta.setEquipoVisitante(equipoVisitante);
        nuevaApuesta.setEstado("Pendiente");
        nuevaApuesta.setResultado("Por jugar"); // Asegúrate de inicializar el campo 'resultado'
        nuevaApuesta.setUsuario(usuario);

        apuestaServicio.registrarApuesta(nuevaApuesta);

        // Actualizar el saldo del usuario
        double nuevoSaldo = saldo - montoApostado;
        usuarioServicios.actualizarSaldo(nombreUsuario, nuevoSaldo);

        // Redirigir al historial de apuestas
        return "redirect:/apuestas?usuario=" + nombreUsuario;
    }


    @PostMapping("/cancelarApuesta")
    public String cancelarApuesta(@RequestParam("idApuesta") Long idApuesta,
                                  @RequestParam("usuario") String nombreUsuario,
                                  RedirectAttributes redirectAttributes) {
        // Obtener la apuesta a cancelar
        Apuesta apuesta = apuestaServicio.obtenerPorId(idApuesta);

        if (apuesta != null && "Pendiente".equals(apuesta.getEstado())) {
            // Cambiar el estado de la apuesta a "Cancelada"
            apuesta.setEstado("Cancelada");
            apuestaServicio.actualizarApuesta(apuesta);
            redirectAttributes.addFlashAttribute("mensaje", "Apuesta cancelada exitosamente.");
        } else {
            redirectAttributes.addFlashAttribute("error", "No se pudo cancelar la apuesta.");
        }

        // Redirigir al historial de apuestas con el parámetro usuario
        return "redirect:/apuestas?usuario=" + nombreUsuario;
    }

}
