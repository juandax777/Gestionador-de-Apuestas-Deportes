package co.ucentral.apuestasdeportivas.ApuestasDeportivas.persistencia.repositorios;

import co.ucentral.apuestasdeportivas.ApuestasDeportivas.persistencia.entidades.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransaccionRepositorio extends JpaRepository<Transaccion, Long> {
}
