package co.ucentral.apuestasdeportivas.ApuestasDeportivas.servicios;

import co.ucentral.apuestasdeportivas.ApuestasDeportivas.persistencia.entidades.Usuario;
import co.ucentral.apuestasdeportivas.ApuestasDeportivas.persistencia.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServicios {

    private final UsuarioRepositorio usuarioRepositorio;

    @Autowired
    public UsuarioServicios(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    // Método para registrar un usuario en la base de datos
    public void registrarUsuario(Usuario usuario) {
        usuarioRepositorio.save(usuario);
    }

    // Método para obtener todos los usuarios
    public List<Usuario> obtenerTodos() {
        return usuarioRepositorio.findAll();
    }

    // Método para autenticar al usuario
    public boolean autenticarUsuario(String usuario, String contrasena) {
        Optional<Usuario> usuarioOpt = usuarioRepositorio.findByUsuarioAndContrasena(usuario, contrasena);
        return usuarioOpt.isPresent();
    }

    // Método para borrar un usuario
    public boolean borrar(Usuario usuario) {
        try {
            usuarioRepositorio.delete(usuario);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}