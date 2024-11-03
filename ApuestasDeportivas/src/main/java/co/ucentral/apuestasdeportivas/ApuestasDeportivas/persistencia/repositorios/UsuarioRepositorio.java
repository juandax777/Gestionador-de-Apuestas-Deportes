package co.ucentral.apuestasdeportivas.ApuestasDeportivas.persistencia.repositorios;

import co.ucentral.apuestasdeportivas.ApuestasDeportivas.persistencia.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {
    Optional<Usuario> findByUsuarioAndContrasena(String usuario, String contrasena);
}