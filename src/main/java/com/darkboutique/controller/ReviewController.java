package com.darkboutique.controller;

import com.darkboutique.domain.Review;
import com.darkboutique.service.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/review")
public class ReviewController {

    private ReviewService reviewService;

    // Constructor manual para inyectar ReviewService
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // Mostrar las reseñas de un producto
    @GetMapping("/list/{productoId}")
    public String listReviews(@PathVariable Long productoId, Model model) {
        List<Review> reviews = reviewService.getReviewsByProducto(productoId);
        model.addAttribute("reviews", reviews);
        return "review/listado"; // Vista en templates/review/listado.html
    }

    // Agregar una reseña para un producto
    @PostMapping("/add")
    public String addReview(@ModelAttribute Review review, @RequestParam Long productoId) {
        // Se asume que el objeto review ya contiene la información necesaria.
        reviewService.addReview(review);
        return "redirect:/review/list/" + productoId;
    }
}

