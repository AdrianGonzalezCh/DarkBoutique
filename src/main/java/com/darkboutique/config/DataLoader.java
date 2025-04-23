package com.darkboutique.config;

import com.darkboutique.dao.RoleRepository;
import com.darkboutique.dao.UsuarioRepository;
import com.darkboutique.domain.Role;
import com.darkboutique.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.List;

@Component
public class DataLoader {

    private final RoleRepository    roleRepo;
    private final UsuarioRepository usuarioRepo;
    private final PasswordEncoder   passwordEncoder;

    @Autowired
    public DataLoader(RoleRepository roleRepo,
                      UsuarioRepository usuarioRepo,
                      PasswordEncoder passwordEncoder) {
        this.roleRepo        = roleRepo;
        this.usuarioRepo     = usuarioRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        // 1) Crear roles si no existen
        if (roleRepo.findByName("ROLE_USER") == null) {
            roleRepo.save(new Role(null, "ROLE_USER"));
        }
        if (roleRepo.findByName("ROLE_ADMIN") == null) {
            roleRepo.save(new Role(null, "ROLE_ADMIN"));
        }

        // 2) Crear usuario administrador si no existe
        String adminUsername = "admin";
        if (usuarioRepo.findByUsername(adminUsername) == null) {
            Usuario admin = new Usuario();
            admin.setUsername(adminUsername);
            // Cambia esta contraseña por algo seguro o extráela de application.properties
            admin.setPassword(passwordEncoder.encode("123"));
            admin.setEmail("admin@darkboutique.com");
            admin.setPoints(0);

            Role adminRole = roleRepo.findByName("ROLE_ADMIN");
            Role userRole  = roleRepo.findByName("ROLE_USER");
            admin.setRoles(List.of(adminRole, userRole));

            usuarioRepo.save(admin);
        }
    }
}
