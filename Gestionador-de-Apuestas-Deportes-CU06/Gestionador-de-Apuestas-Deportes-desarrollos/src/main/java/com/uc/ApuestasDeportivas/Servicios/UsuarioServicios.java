package com.uc.ApuestasDeportivas.Servicios;

import com.uc.ApuestasDeportivas.Persistencia.Entidades.Usuario;
import com.uc.ApuestasDeportivas.Persistencia.Repositorios.UsuarioRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UsuarioServicios {

    private final UsuarioRepositorio usuarioRepositorio;


    public void registrarUsuario(Usuario usuario) {
        usuarioRepositorio.save(usuario);
    }


    public List<Usuario> obtenerTodos() {
        return (List<Usuario>) usuarioRepositorio.findAll();
    }


    public boolean validarUsuario(Usuario usuario) {
        return usuarioRepositorio.findByUsuarioAndContrasena(usuario.getUsuario(), usuario.getContrasena()).isPresent();
    }


    public Usuario obtenerUsuarioPorNombre(String nombreUsuario) {
        return usuarioRepositorio.findById(nombreUsuario).orElse(null);
    }
}
