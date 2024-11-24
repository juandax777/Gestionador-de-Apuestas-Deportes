package com.uc.ApuestasDeportivas.Persistencia.Repositorios;

import com.uc.ApuestasDeportivas.Persistencia.Entidades.Apuesta;
import com.uc.ApuestasDeportivas.Persistencia.Entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApuestaRepositorio extends JpaRepository<Apuesta, Long> {
    List<Apuesta> findByUsuario(Usuario usuario);
}
