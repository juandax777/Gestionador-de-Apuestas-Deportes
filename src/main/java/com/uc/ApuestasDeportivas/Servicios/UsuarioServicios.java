package com.uc.ApuestasDeportivas.Servicios;


import com.uc.ApuestasDeportivas.Persistencia.Entidades.Usuario;
import com.uc.ApuestasDeportivas.Persistencia.Repositorios.UsuarioRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UsuarioServicios {
    UsuarioRepositorio usuarioRepositorio;

    @Autowired

    public void registrarUsuario(Usuario usuario) {
        usuarioRepositorio.save(usuario);
    }

    public List<Usuario> obtenerTodos(){
        List<Usuario> listado = (List<Usuario>) usuarioRepositorio.findAll();
        return listado;
    }
    public boolean borrar(Usuario usuario){
        try{
            usuarioRepositorio.delete(usuario);
        }catch (Exception e){
            return false;
        }
        return true;
    }

}
