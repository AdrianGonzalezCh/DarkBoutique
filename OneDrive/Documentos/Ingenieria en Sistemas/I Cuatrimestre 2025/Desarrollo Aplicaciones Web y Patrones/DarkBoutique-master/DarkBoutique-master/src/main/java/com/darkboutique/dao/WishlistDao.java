package com.darkboutique.dao;

import com.darkboutique.domain.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistDao extends JpaRepository<Wishlist, Long> {
    // Método para obtener la lista de deseos de un usuario específico
    List<Wishlist> findByUsuarioId(Long usuarioId);
}
