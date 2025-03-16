package com.darkboutique.service;

import com.darkboutique.domain.Usuario;

public interface UsuarioService {
    Usuario findById(Long id);
    Usuario save(Usuario usuario);
}
