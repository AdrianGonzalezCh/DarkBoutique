package com.darkboutique.service;

import com.darkboutique.domain.Producto;
import java.util.List;

public interface ProductoService {
    List<Producto> getAll();
    List<Producto> getByCategoria(String categoria);  // ← nuevo
    Producto getById(Long id);
    void save(Producto producto);
    void delete(Long id);
}
