package com.uc.ApuestasDeportivas.Servicios;

import com.uc.ApuestasDeportivas.Persistencia.Entidades.Transaccion;
import com.uc.ApuestasDeportivas.Persistencia.Repositorios.TransaccionRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class TransaccionServicio {

    private final TransaccionRepositorio transaccionRepositorio;

    public void registrarTransaccion(Transaccion transaccion) {
        transaccionRepositorio.save(transaccion);
    }

    public List<Transaccion> obtenerHistorialPorUsuario(String usuario) {
        List<Transaccion> historial = transaccionRepositorio.findByUsuarioUsuario(usuario);
        System.out.println("Transacciones encontradas: " + historial.size()); // Verifica el tamaño
        return historial != null ? historial : List.of();
    }

}