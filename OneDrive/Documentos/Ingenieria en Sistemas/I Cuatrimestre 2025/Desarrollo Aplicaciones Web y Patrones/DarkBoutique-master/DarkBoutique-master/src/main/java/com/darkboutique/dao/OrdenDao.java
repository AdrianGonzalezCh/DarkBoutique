package com.darkboutique.dao;

import com.darkboutique.domain.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdenDao extends JpaRepository<Orden, Long> {
    // Método para obtener las órdenes realizadas por un usuario específico
    List<Orden> findByUsuarioId(Long usuarioId);
}
