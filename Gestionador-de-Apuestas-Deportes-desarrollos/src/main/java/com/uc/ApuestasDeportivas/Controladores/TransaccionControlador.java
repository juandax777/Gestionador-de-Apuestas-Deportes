package com.uc.ApuestasDeportivas.Controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TransaccionControlador {

    private double saldo = 0.0;  // Saldo inicial de 0

    @GetMapping("/ingresoSaldo")
    public String mostrarFormularioIngresoSaldo(Model model) {
        model.addAttribute("mensaje", "");
        return "ingresoSaldo";
    }

    @PostMapping("/ingresoSaldo")
    public String procesarIngresoSaldo(@RequestParam("monto") double monto,
                                       @RequestParam("banco") String banco,
                                       Model model) {
        saldo += monto;  // Agrega el monto al saldo actual
        model.addAttribute("mensaje", "Saldo ingresado exitosamente desde " + banco);
        return "ingresoSaldo";
    }

    // Cambiar la ruta a /paginaPrincipalSaldo para evitar conflicto
    @GetMapping("/paginaPrincipalSaldo")
    public String mostrarPaginaPrincipal(Model model) {
        model.addAttribute("saldo", saldo);  // Muestra el saldo actual en la página principal
        return "paginaPrincipal";
    }
}
