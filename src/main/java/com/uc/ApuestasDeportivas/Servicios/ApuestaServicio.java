package com.uc.ApuestasDeportivas.Servicios;

import com.uc.ApuestasDeportivas.Persistencia.Entidades.Apuesta;
import com.uc.ApuestasDeportivas.Persistencia.Entidades.Partido;
import com.uc.ApuestasDeportivas.Persistencia.Entidades.Transaccion;
import com.uc.ApuestasDeportivas.Persistencia.Entidades.Usuario;
import com.uc.ApuestasDeportivas.Persistencia.Repositorios.ApuestaRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class ApuestaServicio {

    private final ApuestaRepositorio apuestaRepositorio;
    private final UsuarioServicios usuarioServicios;
    private final TransaccionServicio transaccionServicio;

    // Listar apuestas por usuario
    public List<Apuesta> listarPorUsuario(Usuario usuario) {
        return apuestaRepositorio.findByUsuario(usuario);
    }

    // Listar todas las apuestas
    public List<Apuesta> listarTodas() {
        return apuestaRepositorio.findAll();
    }

    // Registrar una nueva apuesta
    public void registrarApuesta(Apuesta apuesta) {
        apuesta.setEstado("Pendiente"); // Estado inicial
        apuesta.setGanancia(null); // Ganancia no calculada inicialmente
        apuestaRepositorio.save(apuesta);
    }

    // Obtener apuesta por ID
    public Apuesta obtenerPorId(Long id) {
        return apuestaRepositorio.findById(id).orElse(null);
    }

    // Actualizar una apuesta existente
    public void actualizarApuesta(Apuesta apuesta) {
        apuestaRepositorio.save(apuesta);
    }

    // Evaluar el resultado de una apuesta
    @Transactional
    public void evaluarResultado(Apuesta apuesta) {
        Partido partido = apuesta.getPartido(); // Obtener el partido vinculado
        int golesLocal = partido.getGolesLocal();
        int golesVisitante = partido.getGolesVisitante();
        double saldoActual = usuarioServicios.obtenerSaldo(apuesta.getUsuario().getUsuario());

        // Determinar el estado y ganancia de la apuesta
        if ("Local".equals(apuesta.getTipoCuota()) && golesLocal > golesVisitante ||
                "Visitante".equals(apuesta.getTipoCuota()) && golesVisitante > golesLocal ||
                "Empate".equals(apuesta.getTipoCuota()) && golesLocal == golesVisitante) {
            // La apuesta fue ganada
            apuesta.setEstado("Ganada");
            double ganancia = apuesta.getMontoApostado() * apuesta.getCuotaSeleccionada();
            apuesta.setGanancia(ganancia);

            // Actualizar saldo del usuario
            usuarioServicios.actualizarSaldo(apuesta.getUsuario().getUsuario(), saldoActual + ganancia);

            // Registrar ganancia como transacción
            transaccionServicio.registrarTransaccion(new Transaccion(
                    null,
                    LocalDateTime.now(),
                    ganancia,
                    "Ganancia",
                    "Apuesta Ganada",
                    saldoActual + ganancia,
                    apuesta.getUsuario()
            ));
        } else {
            // La apuesta fue perdida
            apuesta.setEstado("Perdida");
            apuesta.setGanancia(0.0);

            // Registrar pérdida como transacción
            transaccionServicio.registrarTransaccion(new Transaccion(
                    null,
                    LocalDateTime.now(),
                    0.0,
                    "Pérdida",
                    "Apuesta Perdida",
                    saldoActual,
                    apuesta.getUsuario()
            ));
        }

        // Guardar los cambios en la apuesta
        apuestaRepositorio.save(apuesta);
    }

    // Cancelar una apuesta y devolver el monto
    @Transactional
    public void cancelarApuesta(Apuesta apuesta) {
        if ("Pendiente".equals(apuesta.getEstado())) {
            apuesta.setEstado("Cancelada");
            double montoDevolver = apuesta.getMontoApostado();
            double saldoActual = usuarioServicios.obtenerSaldo(apuesta.getUsuario().getUsuario());
            usuarioServicios.actualizarSaldo(apuesta.getUsuario().getUsuario(), saldoActual + montoDevolver);

            // Registrar devolución como transacción
            transaccionServicio.registrarTransaccion(new Transaccion(
                    null,
                    LocalDateTime.now(),
                    montoDevolver,
                    "Devolución",
                    "Cancelación de Apuesta",
                    saldoActual + montoDevolver,
                    apuesta.getUsuario()
            ));

            apuestaRepositorio.save(apuesta);
        }
    }
}
