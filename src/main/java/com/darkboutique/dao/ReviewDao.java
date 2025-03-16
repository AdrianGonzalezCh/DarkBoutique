package com.darkboutique.dao;

import com.darkboutique.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewDao extends JpaRepository<Review, Long> {
    // Método para obtener las reseñas de un producto específico
    List<Review> findByProductoId(Long productoId);
}
