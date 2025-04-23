package com.darkboutique.service.impl;

import com.darkboutique.dao.UsuarioRepository;
import com.darkboutique.domain.Usuario;
import com.darkboutique.service.UsuarioService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepo;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return usuarioRepo.findById(id);
    }

    @Override
    public Usuario save(Usuario usuario) {
        return usuarioRepo.save(usuario);
    }

    @Override
    public Optional<Usuario> findByUsername(String username) {
        // El repositorio ya devuelve Optional<Usuario>
        return usuarioRepo.findByUsername(username);
    }
}
