package com.darkboutique.controller;

import com.darkboutique.domain.Usuario;
import com.darkboutique.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    private UsuarioService usuarioService;

    // Constructor manual para inyectar UsuarioService
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/perfil/{usuarioId}")
    public String perfil(@PathVariable Long usuarioId, Model model) {
        Usuario usuario = usuarioService.findById(usuarioId);
        model.addAttribute("usuario", usuario);
        return "usuario/perfil"; // Vista en templates/usuario/perfil.html
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuario/register"; // Vista en templates/usuario/register.html
    }

    @PostMapping("/register")
    public String registerUsuario(@ModelAttribute Usuario usuario) {
        Usuario savedUsuario = usuarioService.save(usuario);
        return "redirect:/usuario/perfil/" + savedUsuario.getId();
    }
}
