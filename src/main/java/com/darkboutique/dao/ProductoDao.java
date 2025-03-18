package com.darkboutique.dao;

import com.darkboutique.domain.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoDao extends JpaRepository<Producto, Long> {
    List<Producto> findByCategoria(String categoria);
}
