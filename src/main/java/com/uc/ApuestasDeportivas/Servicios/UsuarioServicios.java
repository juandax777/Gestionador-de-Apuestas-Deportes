package com.uc.ApuestasDeportivas.Servicios;

import com.uc.ApuestasDeportivas.Persistencia.Entidades.Usuario;
import com.uc.ApuestasDeportivas.Persistencia.Repositorios.UsuarioRepositorio;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UsuarioServicios {

    private final UsuarioRepositorio usuarioRepositorio;

    public void registrarUsuario(Usuario usuario) {
        usuarioRepositorio.save(usuario);
    }

    public List<Usuario> obtenerTodos() {
        return usuarioRepositorio.findAll();
    }

    public boolean validarUsuario(Usuario usuario) {
        return usuarioRepositorio.findByUsuarioAndContrasena(usuario.getUsuario(), usuario.getContrasena()).isPresent();
    }

    public Usuario obtenerUsuarioPorNombre(String nombreUsuario) {
        return usuarioRepositorio.findById(nombreUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + nombreUsuario));
    }

    public double obtenerSaldo(String nombreUsuario) {
        return obtenerUsuarioPorNombre(nombreUsuario).getSaldo();
    }

    @Transactional
    public void actualizarSaldo(String nombreUsuario, double nuevoSaldo) {
        usuarioRepositorio.actualizarSaldo(nombreUsuario, nuevoSaldo);
    }

}
