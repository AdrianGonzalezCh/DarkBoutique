package com.darkboutique.service.impl;

import com.darkboutique.dao.ReviewDao;
import com.darkboutique.domain.Review;
import com.darkboutique.service.ReviewService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private ReviewDao reviewDao;

    // Constructor manual para inyectar ReviewDao
    public ReviewServiceImpl(ReviewDao reviewDao) {
        this.reviewDao = reviewDao;
    }

    @Override
    public Review addReview(Review review) {
        return reviewDao.save(review);
    }

    @Override
    public List<Review> getReviewsByProducto(Long productoId) {
        return reviewDao.findByProductoId(productoId);
    }
}
