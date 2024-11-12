package com.uc.ApuestasDeportivas.Servicios;

import com.uc.ApuestasDeportivas.Persistencia.Entidades.Apuesta;
import com.uc.ApuestasDeportivas.Persistencia.Repositorios.ApuestaRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ApuestaServicio {

    private final ApuestaRepositorio apuestaRepositorio;

    public List<Apuesta> listarTodas() {
        return apuestaRepositorio.findAll();
    }

    public Apuesta registrarApuesta(Apuesta apuesta) {
        return apuestaRepositorio.save(apuesta);
    }
}
