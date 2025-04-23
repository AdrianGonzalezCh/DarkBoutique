package com.darkboutique.controller;

import com.darkboutique.dao.RoleRepository;
import com.darkboutique.dao.UsuarioRepository;
import com.darkboutique.domain.Role;
import com.darkboutique.domain.Usuario;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UsuarioRepository usuarioRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UsuarioRepository usuarioRepo,
                          RoleRepository roleRepo,
                          PasswordEncoder passwordEncoder) {
        this.usuarioRepo = usuarioRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerForm(Model m) {
        m.addAttribute("usuario", new Usuario());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute Usuario u) {
        u.setPassword(passwordEncoder.encode(u.getPassword()));
        Role userRole = roleRepo.findByName("ROLE_USER");
        u.setRoles(List.of(userRole));
        usuarioRepo.save(u);
        return "redirect:/login?registered";
    }
}
