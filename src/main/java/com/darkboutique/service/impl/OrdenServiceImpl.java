package com.darkboutique.service.impl;

import com.darkboutique.dao.OrdenDao;
import com.darkboutique.domain.Orden;
import com.darkboutique.service.OrdenService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdenServiceImpl implements OrdenService {

    private OrdenDao ordenDao;

    // Constructor manual para inyectar OrdenDao
    public OrdenServiceImpl(OrdenDao ordenDao) {
        this.ordenDao = ordenDao;
    }

    @Override
    public Orden createOrder(Orden orden) {
        return ordenDao.save(orden);
    }

    @Override
    public List<Orden> getOrdersByUsuario(Long usuarioId) {
        return ordenDao.findByUsuarioId(usuarioId);
    }
}
