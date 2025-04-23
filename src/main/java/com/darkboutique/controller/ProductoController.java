package com.darkboutique.controller;

import com.darkboutique.domain.Producto;
import com.darkboutique.service.ProductoService;
import com.darkboutique.service.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProductoController {

    private final ProductoService productoService;
    private final ReviewService reviewService;

    public ProductoController(ProductoService productoService,
                              ReviewService reviewService) {
        this.productoService = productoService;
        this.reviewService   = reviewService;
    }

    @GetMapping("/productos")
    public String listarProductos(
            @RequestParam(required = false) String categoria,
            Model model) {

        List<Producto> lista;
        if (categoria != null && !categoria.isBlank()) {
            lista = productoService.getByCategoria(categoria);
        } else {
            lista = productoService.getAll();
        }

        model.addAttribute("productos", lista);
        model.addAttribute("categoria", categoria);  // para mantener la selección
        return "producto/listado";
    }

    @GetMapping("/producto/{id}")
    public String detalleProducto(
            @PathVariable Long id,
            @RequestParam(name = "reviews", required = false) String reviewsFlag,
            Model model) {

        Producto p = productoService.getById(id);
        if (p == null) {
            throw new IllegalArgumentException("Producto no encontrado: " + id);
        }
        model.addAttribute("producto", p);

        // Si en la URL hay ?reviews, cargo también las reseñas
        if (reviewsFlag != null) {
            var reviews = reviewService.getReviewsByProducto(id);
            model.addAttribute("reviews", reviews);
        }

        return "producto/detalle";
    }
}
