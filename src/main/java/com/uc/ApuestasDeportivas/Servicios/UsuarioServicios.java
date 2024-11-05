package com.uc.ApuestasDeportivas.Servicios;

import com.uc.ApuestasDeportivas.Persistencia.Entidades.Usuario;
import com.uc.ApuestasDeportivas.Persistencia.Repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServicios {

    private final UsuarioRepositorio usuarioRepositorio;

    @Autowired
    public UsuarioServicios(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    public void registrarUsuario(Usuario usuario) {
        usuarioRepositorio.save(usuario);
    }

    public List<Usuario> obtenerTodos() {
        return (List<Usuario>) usuarioRepositorio.findAll();
    }

    public Usuario obtenerUsuarioPorCredenciales(String usuario, String contrasena) {
        return usuarioRepositorio.findByUsuarioAndContrasena(usuario, contrasena).orElse(null);
    }

    // En UsuarioServicios.java
    public boolean validarUsuario(Usuario usuario) {
        // Usa el método personalizado findByUsuarioAndContrasena para validar credenciales
        return usuarioRepositorio.findByUsuarioAndContrasena(usuario.getUsuario(), usuario.getContrasena()).isPresent();
    }


}
