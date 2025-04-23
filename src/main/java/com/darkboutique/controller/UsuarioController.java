package com.darkboutique.controller;

import com.darkboutique.domain.Usuario;
import com.darkboutique.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Muestra el perfil de un usuario por su ID.
     */
    @GetMapping("/perfil/{usuarioId}")
    public String perfil(@PathVariable Long usuarioId, Model model) {
        Usuario usuario = usuarioService.findById(usuarioId)
            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + usuarioId));
        model.addAttribute("usuario", usuario);
        return "usuario/perfil"; // templates/usuario/perfil.html
    }

    /**
     * Formulario de registro de nuevo usuario.
     */
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuario/register"; // templates/usuario/register.html
    }

    /**
     * Procesa el registro de un usuario y redirige a su perfil.
     */
    @PostMapping("/register")
    public String registerUsuario(@ModelAttribute Usuario usuario) {
        Usuario savedUsuario = usuarioService.save(usuario);
        return "redirect:/usuario/perfil/" + savedUsuario.getId();
    }
}
