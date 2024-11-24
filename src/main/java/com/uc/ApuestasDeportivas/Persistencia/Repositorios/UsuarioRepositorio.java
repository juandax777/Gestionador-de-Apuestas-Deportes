package com.uc.ApuestasDeportivas.Persistencia.Repositorios;

import com.uc.ApuestasDeportivas.Persistencia.Entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {

    Optional<Usuario> findByUsuarioAndContrasena(String usuario, String contrasena);

    @Modifying
    @Query("UPDATE Usuario u SET u.saldo = :saldo WHERE u.usuario = :usuario")
    void actualizarSaldo(String usuario, double saldo);
}
