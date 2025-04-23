package com.darkboutique.dao;
import com.darkboutique.domain.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductoDao extends JpaRepository<Producto, Long> {
    // JPA infiere SELECT * FROM producto WHERE categoria = ?1
    List<Producto> findByCategoria(String categoria);
}
