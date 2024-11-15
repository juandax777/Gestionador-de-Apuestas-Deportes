package com.uc.ApuestasDeportivas.Servicios;

import com.uc.ApuestasDeportivas.Persistencia.Entidades.Transaccion;
import com.uc.ApuestasDeportivas.Persistencia.Entidades.Usuario;
import com.uc.ApuestasDeportivas.Persistencia.Repositorios.TransaccionRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class TransaccionServicio {

    private final TransaccionRepositorio transaccionRepositorio;


    public void registrarTransaccion(double monto, String tipo, Usuario usuario) {
        Transaccion transaccion = new Transaccion();
        transaccion.setFecha(LocalDateTime.now());
        transaccion.setMonto(monto);
        transaccion.setTipo(tipo);
        transaccion.setUsuario(usuario);
        transaccionRepositorio.save(transaccion);
    }
}
