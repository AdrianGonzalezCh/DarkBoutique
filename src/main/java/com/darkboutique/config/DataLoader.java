package com.darkboutique.config;

import com.darkboutique.dao.RoleRepository;
import com.darkboutique.dao.UsuarioRepository;
import com.darkboutique.domain.Role;
import com.darkboutique.domain.Usuario;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        Optional<Usuario> adminOptional = usuarioRepo.findByUsername(adminUsername);

        Role roleUser  = roleRepo.findByName("ROLE_USER");
        Role roleAdmin = roleRepo.findByName("ROLE_ADMIN");

        if (adminOptional.isEmpty()) {
            // No existe: crear usuario admin
            Usuario admin = new Usuario();
            admin.setUsername(adminUsername);
            admin.setPassword(passwordEncoder.encode("123"));
            admin.setEmail("admin@darkboutique.com");
            admin.setPoints(0);
            admin.setRoles(List.of(roleAdmin, roleUser));
            usuarioRepo.save(admin);

            System.out.println(">>> Usuario 'admin' creado automáticamente.");
        } else {
            // Ya existe: actualizar roles si falta alguno
            Usuario admin = adminOptional.get();
            List<Role> roles = new ArrayList<>(admin.getRoles());

            if (!roles.contains(roleAdmin)) {
                roles.add(roleAdmin);
            }
            if (!roles.contains(roleUser)) {
                roles.add(roleUser);
            }

            admin.setRoles(roles);
            usuarioRepo.save(admin);

            System.out.println(">>> Usuario 'admin' ya existe. Roles actualizados si era necesario.");
        }
    }
}
