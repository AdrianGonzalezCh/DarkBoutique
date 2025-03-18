package com.darkboutique.service.impl;

import com.darkboutique.dao.UsuarioDao;
import com.darkboutique.domain.Usuario;
import com.darkboutique.service.UsuarioService;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private UsuarioDao usuarioDao;

    // Constructor manual para inyectar UsuarioDao
    public UsuarioServiceImpl(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    @Override
    public Usuario findById(Long id) {
        return usuarioDao.findById(id).orElse(null);
    }

    @Override
    public Usuario save(Usuario usuario) {
        return usuarioDao.save(usuario);
    }
}
