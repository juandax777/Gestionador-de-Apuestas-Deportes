package com.uc.ApuestasDeportivas.Controladores;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

@AllArgsConstructor
@Controller
public class UsuarioControlador {

    UsuarioServicios usuarioServicio;

    @GetMapping("/registro")
    public String mostrarFormularioDeRegistro(Model model) {
        Usuario usuario = new Usuario();

        model.addAttribute("elusuario", usuario);
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(Usuario usuario, Model model) {
        usuarioServicio.registrarUsuario(usuario);
        model.addAttribute("mensaje", "Usuario registrado exitosamente");
        return "iniciosesion";


    }

    public List<Usuario> obtenerTodos() {
        return usuarioServicio.obtenerTodos();
    }


}
