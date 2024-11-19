package com.uc.ApuestasDeportivas.Controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TransaccionControlador {

    private double saldo = 0.0;  // Saldo inicial de 0
    private List<String> historialTransacciones = new ArrayList<>();

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
        String transaccion = "Ingresado: $" + monto + " desde " + banco;
        historialTransacciones.add(transaccion);
        model.addAttribute("mensaje", "Saldo ingresado exitosamente desde " + banco);
        return "ingresoSaldo";
    }

    @GetMapping("/paginaPrincipalSaldo")
    public String mostrarPaginaPrincipal(Model model) {
        model.addAttribute("saldo", saldo);
        return "paginaPrincipal";
    }

    // Nuevo método para mostrar el historial de transacciones
    @GetMapping("/historialTransacciones")
    public String verHistorialTransacciones(Model model) {
        model.addAttribute("historialTransacciones", historialTransacciones);
        return "historialTransacciones";
    }
}