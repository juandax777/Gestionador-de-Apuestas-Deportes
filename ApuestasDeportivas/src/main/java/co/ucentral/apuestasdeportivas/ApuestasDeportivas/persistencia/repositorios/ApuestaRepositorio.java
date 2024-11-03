package co.ucentral.apuestasdeportivas.ApuestasDeportivas.persistencia.repositorios;

import co.ucentral.apuestasdeportivas.ApuestasDeportivas.persistencia.entidades.Apuesta;
import org.springframework.data.repository.CrudRepository;

public interface ApuestaRepositorio extends CrudRepository<Apuesta, Long> {
}