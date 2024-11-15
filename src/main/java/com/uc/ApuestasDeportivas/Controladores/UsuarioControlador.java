package com.uc.ApuestasDeportivas.Controladores;

import com.uc.ApuestasDeportivas.Persistencia.Entidades.Usuario;
import com.uc.ApuestasDeportivas.Servicios.UsuarioServicios;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@AllArgsConstructor
@Controller
public class UsuarioControlador {

    private final UsuarioServicios usuarioServicio;

    // Muestra el formulario de registro de usuario
    @GetMapping("/registro")
    public String mostrarFormularioDeRegistro(Model model) {
        model.addAttribute("usuario", new Usuario()); // Se asegura de que el objeto sea "usuario"
        return "registro"; // Este nombre debe coincidir con el archivo registro.html
    }

    // Procesa el registro de usuario
    @PostMapping("/registro")
    public String registrarUsuario(Usuario usuario, Model model) {
        usuarioServicio.registrarUsuario(usuario);
        model.addAttribute("mensaje", "Registro completo");
        return "registro"; // Muestra el mensaje en la misma página de registro
    }


    // Página de confirmación después de registrarse
    @GetMapping("/confirmacion")
    public String mostrarConfirmacion(Model model) {
        model.addAttribute("mensaje", "Usuario registrado exitosamente");
        return "confirmacion"; // Crea un confirmacion.html para mostrar el mensaje
    }

    // Muestra una lista de todos los usuarios registrados
    @GetMapping("/usuarios")
    public String listarUsuarios(Model model) {
        List<Usuario> usuarios = usuarioServicio.obtenerTodos();
        model.addAttribute("usuarios", usuarios);
        return "listaUsuarios"; // Crea listaUsuarios.html para mostrar la lista
    }

    @GetMapping("/login")
    public String mostrarFormularioLogin(Model model) {
        model.addAttribute("usuario", new Usuario()); // Se asegura de que se pase un objeto usuario vacío
        return "login"; // Debe coincidir con el nombre del archivo HTML login.html
    }

    // En UsuarioControlador.java

    @PostMapping("/login")
    public String procesarLogin(Usuario usuario, Model model) {
        // Lógica de autenticación aquí
        if (usuarioServicio.validarUsuario(usuario)) {
            return "redirect:/paginaPrincipal";
        } else {
            model.addAttribute("error", "Credenciales incorrectas");
            return "login";
        }
    }


    @GetMapping("/paginaPrincipal")
    public String mostrarPaginaPrincipal() {
        return "paginaPrincipal"; // Este nombre debe coincidir con el archivo paginaPrincipal.html
    }

    @GetMapping("/listaUsuarios")
    public String mostrarListaUsuarios(Model model) {
        List<Usuario> usuarios = usuarioServicio.obtenerTodos();
        model.addAttribute("usuarios", usuarios);
        return "listaUsuarios"; // Este nombre debe coincidir con el archivo listaUsuarios.html
    }

    @PostMapping("/inicioSesion")  // Cambia el nombre del mapeo para evitar conflictos
    public String iniciarSesion(Usuario usuario, Model model) {
        // Utiliza validarUsuario para verificar las credenciales
        if (usuarioServicio.validarUsuario(usuario)) {
            return "redirect:/paginaPrincipal";
        } else {
            model.addAttribute("error", "Credenciales incorrectas");
            return "login";
        }
    }


}
