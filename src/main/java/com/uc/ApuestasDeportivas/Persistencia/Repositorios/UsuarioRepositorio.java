package com.uc.ApuestasDeportivas.Persistencia.Repositorios;

import com.uc.ApuestasDeportivas.Persistencia.Entidades.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface UsuarioRepositorio extends CrudRepository<Usuario, String> {
    Optional<Usuario> findByUsuarioAndContrasena(String usuario, String contrasena);
}

