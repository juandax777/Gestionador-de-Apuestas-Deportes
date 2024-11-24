package com.uc.ApuestasDeportivas.Controladores;

import com.uc.ApuestasDeportivas.Persistencia.Entidades.Transaccion;
import com.uc.ApuestasDeportivas.Persistencia.Entidades.Usuario;
import com.uc.ApuestasDeportivas.Servicios.TransaccionServicio;
import com.uc.ApuestasDeportivas.Servicios.UsuarioServicios;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@AllArgsConstructor
public class TransaccionControlador {

    private final TransaccionServicio transaccionServicio;
    private final UsuarioServicios usuarioServicios;

    @GetMapping("/ingresoSaldo")
    public String mostrarFormularioIngresoSaldo(@RequestParam("usuario") String nombreUsuario, Model model) {
        double saldoActual = usuarioServicios.obtenerSaldo(nombreUsuario);
        model.addAttribute("saldo", saldoActual);
        model.addAttribute("usuario", nombreUsuario);
        model.addAttribute("mensaje", "");
        return "ingresoSaldo";
    }

    @PostMapping("/ingresoSaldo")
    public String procesarIngresoSaldo(@RequestParam("monto") double monto,
                                       @RequestParam("banco") String banco,
                                       @RequestParam("usuario") String nombreUsuario,
                                       Model model) {
        if (monto <= 0) {
            model.addAttribute("mensaje", "El monto debe ser mayor a 0.");
            model.addAttribute("saldo", usuarioServicios.obtenerSaldo(nombreUsuario));
            model.addAttribute("usuario", nombreUsuario);
            return "ingresoSaldo";
        }

        // Actualizar saldo en el usuario
        double saldoActual = usuarioServicios.obtenerSaldo(nombreUsuario);
        double nuevoSaldo = saldoActual + monto;
        usuarioServicios.actualizarSaldo(nombreUsuario, nuevoSaldo);

        // Registrar la transacción
        Usuario usuario = usuarioServicios.obtenerUsuarioPorNombre(nombreUsuario);
        Transaccion transaccion = new Transaccion(null, LocalDateTime.now(), monto, "Ingreso", banco, nuevoSaldo, usuario);
        transaccionServicio.registrarTransaccion(transaccion);

        model.addAttribute("mensaje", "Saldo ingresado exitosamente desde " + banco);
        model.addAttribute("saldo", nuevoSaldo);
        model.addAttribute("usuario", nombreUsuario);
        return "ingresoSaldo";
    }

    @GetMapping("/historialTransacciones")
    public String verHistorialTransacciones(@RequestParam("usuario") String nombreUsuario, Model model) {
        List<Transaccion> historial = transaccionServicio.obtenerHistorialPorUsuario(nombreUsuario);

        // Validar si hay transacciones para evitar problemas en la plantilla
        if (historial == null || historial.isEmpty()) {
            model.addAttribute("mensaje", "No se encontraron transacciones.");
            model.addAttribute("historialTransacciones", List.of());
        } else {
            model.addAttribute("historialTransacciones", historial);
        }

        model.addAttribute("usuario", nombreUsuario); // Enviar usuario para los botones
        return "historialTransacciones";
    }
}
