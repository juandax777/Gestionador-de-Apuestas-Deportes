package co.ucentral.apuestasdeportivas.ApuestasDeportivas.controladores;

import co.ucentral.apuestasdeportivas.ApuestasDeportivas.persistencia.entidades.Transaccion;
import co.ucentral.apuestasdeportivas.ApuestasDeportivas.servicios.TransaccionServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TransaccionControlador {

    @Autowired
    private TransaccionServicios transaccionServicios;

    @GetMapping("/ingresoSaldo")
    public String mostrarFormularioIngresoSaldo() {
        return "ingresoSaldo";
    }

    @PostMapping("/ingresoSaldo")
    public String procesarIngresoSaldo(@RequestParam("monto") double monto, Model model) {
        transaccionServicios.registrarTransaccionAutomatica(monto, "INGRESO");
        model.addAttribute("mensaje", "Saldo ingresado exitosamente");
        return "ingresoSaldo";
    }
}
