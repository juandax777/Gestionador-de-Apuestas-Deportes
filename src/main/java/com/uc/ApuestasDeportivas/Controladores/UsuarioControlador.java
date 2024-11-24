package com.uc.ApuestasDeportivas.Controladores;

import com.uc.ApuestasDeportivas.Persistencia.Entidades.Usuario;
import com.uc.ApuestasDeportivas.Servicios.UsuarioServicios;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@AllArgsConstructor
@Controller
public class UsuarioControlador {

    private final UsuarioServicios usuarioServicio;

    @GetMapping("/registro")
    public String mostrarFormularioDeRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(@ModelAttribute Usuario usuario, Model model) {
        usuarioServicio.registrarUsuario(usuario);
        return "redirect:/confirmacion";
    }

    @GetMapping("/confirmacion")
    public String mostrarConfirmacion(Model model) {
        model.addAttribute("mensaje", "Usuario registrado exitosamente.");
        return "confirmacion";
    }

    @GetMapping("/login")
    public String mostrarFormularioLogin(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@ModelAttribute Usuario usuario, Model model) {
        if (usuarioServicio.validarUsuario(usuario)) {
            return "redirect:/paginaPrincipal?usuario=" + usuario.getUsuario();
        } else {
            model.addAttribute("error", "Credenciales incorrectas. Intenta de nuevo.");
            return "login";
        }
    }

    @GetMapping("/paginaPrincipal")
    public String mostrarPaginaPrincipal(@RequestParam("usuario") String nombreUsuario, Model model) {
        double saldo = usuarioServicio.obtenerSaldo(nombreUsuario);
        model.addAttribute("usuario", nombreUsuario);
        model.addAttribute("saldo", saldo);
        return "paginaPrincipal";
    }

    @GetMapping("/listaUsuarios")
    public String mostrarListaUsuarios(Model model) {
        List<Usuario> usuarios = usuarioServicio.obtenerTodos();
        model.addAttribute("usuarios", usuarios);
        return "listaUsuarios";
    }
}
