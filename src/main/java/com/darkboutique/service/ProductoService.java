package com.darkboutique.service;

import com.darkboutique.domain.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoService {
    List<Producto> obtenerTodos();
    List<Producto> obtenerPorCategoria(String categoria);
    Optional<Producto> obtenerPorId(Long id);
    Producto guardar(Producto producto);
    void eliminar(Long id);
}
