package co.ucentral.apuestasdeportivas.ApuestasDeportivas.servicios;

import co.ucentral.apuestasdeportivas.ApuestasDeportivas.persistencia.entidades.Apuesta;
import co.ucentral.apuestasdeportivas.ApuestasDeportivas.persistencia.repositorios.ApuestaRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApuestaServicio {

    private final ApuestaRepositorio apuestaRepositorio;

    public ApuestaServicio(ApuestaRepositorio apuestaRepositorio) {
        this.apuestaRepositorio = apuestaRepositorio;
    }

    public List<Apuesta> listarTodas() {
        return (List<Apuesta>) apuestaRepositorio.findAll();
    }
}