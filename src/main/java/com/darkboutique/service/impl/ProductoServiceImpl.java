package com.darkboutique.service.impl;

import com.darkboutique.dao.ProductoDao;
import com.darkboutique.domain.Producto;
import com.darkboutique.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoDao productoDao;

    @Override
    public List<Producto> getAll() {
        return productoDao.findAll();
    }

    @Override
    public List<Producto> getByCategoria(String categoria) {
        return productoDao.findByCategoria(categoria);
    }

    @Override
    public Producto getById(Long id) {
        return productoDao.findById(id).orElse(null);
    }

    @Override
    public void save(Producto producto) {
        productoDao.save(producto);
    }

    @Override
    public void delete(Long id) {
        productoDao.deleteById(id);
    }
}
