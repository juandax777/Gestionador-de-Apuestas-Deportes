package com.uc.ApuestasDeportivas.Servicios;

import com.uc.ApuestasDeportivas.Persistencia.Entidades.Apuesta;
import com.uc.ApuestasDeportivas.Persistencia.Entidades.Usuario;
import com.uc.ApuestasDeportivas.Persistencia.Repositorios.ApuestaRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ApuestaServicio {

    private final ApuestaRepositorio apuestaRepositorio;


    public List<Apuesta> listarPorUsuario(Usuario usuario) {
        return apuestaRepositorio.findByUsuario(usuario);
    }

    public List<Apuesta> listarTodas() {
        return apuestaRepositorio.findAll();
    }


    public void registrarApuesta(Apuesta apuesta) {
        apuestaRepositorio.save(apuesta);
    }

    public Apuesta obtenerPorId(Long id) {
        return apuestaRepositorio.findById(id).orElse(null);
    }

    public void actualizarApuesta(Apuesta apuesta) {
        apuestaRepositorio.save(apuesta);
    }
}
