package com.darkboutique.service.impl;

import com.darkboutique.dao.ProductoDao;
import com.darkboutique.domain.Producto;
import com.darkboutique.service.ProductoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService {

    private ProductoDao productoDao;

    // Constructor manual para inyectar ProductoDao
    public ProductoServiceImpl(ProductoDao productoDao) {
        this.productoDao = productoDao;
    }

    @Override
    public List<Producto> obtenerTodos() {
        return productoDao.findAll();
    }

    @Override
    public List<Producto> obtenerPorCategoria(String categoria) {
        return productoDao.findByCategoria(categoria);
    }

    @Override
    public Optional<Producto> obtenerPorId(Long id) {
        return productoDao.findById(id);
    }

    @Override
    public Producto guardar(Producto producto) {
        return productoDao.save(producto);
    }

    @Override
    public void eliminar(Long id) {
        productoDao.deleteById(id);
    }
}
