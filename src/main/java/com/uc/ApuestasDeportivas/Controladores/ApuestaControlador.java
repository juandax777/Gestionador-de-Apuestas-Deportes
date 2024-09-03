package com.uc.ApuestasDeportivas.Controladores;

import com.uc.ApuestasDeportivas.Persistencia.Entidades.Apuesta;
import com.uc.ApuestasDeportivas.Persistencia.Entidades.Partido;
import com.uc.ApuestasDeportivas.Persistencia.Entidades.Usuario;
import com.uc.ApuestasDeportivas.Servicios.ApuestaServicio;
import com.uc.ApuestasDeportivas.Servicios.PartidoServicio;
import com.uc.ApuestasDeportivas.Servicios.UsuarioServicios;
import lombok.AllArgsConstructor;
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

    private final ApuestaServicio apuestaServicio;
    private final UsuarioServicios usuarioServicios;
    private final PartidoServicio partidoServicio;

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

        // Obtener el partido asociado
        Partido partido = partidoServicio.obtenerPartidoPorEquiposYLiga(equipoLocal, equipoVisitante, liga);

        if (partido == null) {
            model.addAttribute("error", "El partido seleccionado no existe.");
            return "resumenApuesta";
        }

        // Crear y guardar la apuesta
        Usuario usuario = usuarioServicios.obtenerUsuarioPorNombre(nombreUsuario);
        Apuesta nuevaApuesta = new Apuesta();
        nuevaApuesta.setTipoCuota(tipoCuota);
        nuevaApuesta.setCuotaSeleccionada(cuotaSeleccionada);
        nuevaApuesta.setMontoApostado(montoApostado);
        nuevaApuesta.setEquipoLocal(equipoLocal);
        nuevaApuesta.setEquipoVisitante(equipoVisitante);
        nuevaApuesta.setEstado("Pendiente");
        nuevaApuesta.setUsuario(usuario);
        nuevaApuesta.setPartido(partido); // Asignar el partido asociado

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

            // Devolver el monto al saldo del usuario
            usuarioServicios.actualizarSaldo(nombreUsuario,
                    usuarioServicios.obtenerSaldo(nombreUsuario) + apuesta.getMontoApostado());

            redirectAttributes.addFlashAttribute("mensaje", "Apuesta cancelada y monto devuelto exitosamente.");
        } else {
            redirectAttributes.addFlashAttribute("error", "No se pudo cancelar la apuesta.");
        }

        return "redirect:/apuestas?usuario=" + nombreUsuario;
    }

    @PostMapping("/verResultado")
    public String verResultado(@RequestParam("idApuesta") Long idApuesta,
                               RedirectAttributes redirectAttributes) {
        Apuesta apuesta = apuestaServicio.obtenerPorId(idApuesta);

        if (apuesta != null) {
            // Evalúa la apuesta y actualiza el resultado
            apuestaServicio.evaluarResultado(apuesta);

            // Añade un mensaje de éxito
            redirectAttributes.addFlashAttribute("mensaje", "Resultado evaluado correctamente. Estado actualizado.");
        } else {
            // Manejo de error si no se encuentra la apuesta
            redirectAttributes.addFlashAttribute("error", "No se pudo evaluar el resultado.");
        }

        return "redirect:/apuestas?usuario=" + apuesta.getUsuario().getUsuario();
    }
}