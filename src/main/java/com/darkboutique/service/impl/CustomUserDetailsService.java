package com.darkboutique.service.impl;

import com.darkboutique.dao.UsuarioRepository;
import com.darkboutique.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Carga los datos de un Usuario y sus Roles desde la base de datos
 * para la autenticación y autorización en Spring Security.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepo;

    @Autowired
    public CustomUserDetailsService(UsuarioRepository usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1) Obtener el Usuario de la base de datos (ya con Optional)
        Usuario usuario = usuarioRepo.findByUsername(username)
            .orElseThrow(() ->
                new UsernameNotFoundException("Usuario no encontrado: " + username)
            );

        // 2) Mapear cada Role.name a un SimpleGrantedAuthority
        List<GrantedAuthority> authorities = usuario.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority(role.getName()))
            .collect(Collectors.toList());

        // 3) Construir y devolver el User de Spring Security
        return new User(
            usuario.getUsername(),
            usuario.getPassword(),
            authorities
        );
    }
}
