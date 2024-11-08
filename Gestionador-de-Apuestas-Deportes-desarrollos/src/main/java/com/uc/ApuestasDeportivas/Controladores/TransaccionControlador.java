package com.uc.ApuestasDeportivas.Controladores;

import com.uc.ApuestasDeportivas.Persistencia.Entidades.Usuario;
import com.uc.ApuestasDeportivas.Servicios.TransaccionServicio;
import com.uc.ApuestasDeportivas.Servicios.UsuarioServicios;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@AllArgsConstructor
@Controller
public class TransaccionControlador {

    private final TransaccionServicio transaccionServicio;
    private final UsuarioServicios usuarioServicios;

    @GetMapping("/ingresoSaldo")
    public String mostrarFormularioIngresoSaldo() {
        return "ingresoSaldo"; // Nombre de la vista HTML para ingreso de saldo
    }

    @PostMapping("/ingresoSaldo")
    public String procesarIngresoSaldo(@RequestParam("monto") double monto, @RequestParam("usuarioId") String usuarioId, Model model) {
        // Usamos el nombre de usuario (usuarioId) para buscar el usuario
        Usuario usuario = usuarioServicios.obtenerUsuarioPorNombre(usuarioId);
        if (usuario != null) {
            transaccionServicio.registrarTransaccion(monto, "INGRESO", usuario);
            model.addAttribute("mensaje", "Saldo ingresado exitosamente");
        } else {
            model.addAttribute("error", "Usuario no encontrado");
        }
        return "ingresoSaldo";
    }
}
