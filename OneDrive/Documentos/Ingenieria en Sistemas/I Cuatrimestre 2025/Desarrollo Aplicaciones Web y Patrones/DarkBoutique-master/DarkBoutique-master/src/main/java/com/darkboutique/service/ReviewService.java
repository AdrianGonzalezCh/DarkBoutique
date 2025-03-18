package com.darkboutique.service;

import com.darkboutique.domain.Review;
import java.util.List;

public interface ReviewService {
    Review addReview(Review review);
    List<Review> getReviewsByProducto(Long productoId);
}
