package co.ucentral.apuestasdeportivas.ApuestasDeportivas.controladores;

import co.ucentral.apuestasdeportivas.ApuestasDeportivas.persistencia.entidades.Usuario;
import co.ucentral.apuestasdeportivas.ApuestasDeportivas.servicios.UsuarioServicios;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@AllArgsConstructor
@Controller
public class UsuarioControlador {

    private final UsuarioServicios usuarioServicio;

    @GetMapping("/login")
    public String mostrarFormularioDeLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(Usuario usuario, Model model) {
        boolean autenticado = usuarioServicio.autenticarUsuario(usuario.getUsuario(), usuario.getContrasena());

        if (autenticado) {
            return "redirect:/"; // Redirige al index si las credenciales son correctas
        } else {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
            return "login"; // Muestra el formulario de login con un mensaje de error
        }
    }

    @GetMapping("/registro")
    public String mostrarFormularioDeRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(Usuario usuario, Model model) {
        usuarioServicio.registrarUsuario(usuario);
        model.addAttribute("mensaje", "Usuario registrado exitosamente");
        return "redirect:/login";
    }
}
