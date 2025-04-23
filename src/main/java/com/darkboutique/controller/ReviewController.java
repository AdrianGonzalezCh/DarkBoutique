package com.darkboutique.controller;

import com.darkboutique.domain.Producto;
import com.darkboutique.domain.Review;
import com.darkboutique.domain.Usuario;
import com.darkboutique.service.ProductoService;
import com.darkboutique.service.ReviewService;
import com.darkboutique.service.UsuarioService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;
    private final ProductoService productoService;
    private final UsuarioService usuarioService;

    public ReviewController(ReviewService reviewService,
                            ProductoService productoService,
                            UsuarioService usuarioService) {
        this.reviewService   = reviewService;
        this.productoService = productoService;
        this.usuarioService  = usuarioService;
    }

    /**
     * Añade una reseña a un producto. 
     * Recibe:
     *  - productoId: el id del producto
     *  - comentario: el texto de la reseña
     *  - rating: puntuación (opcional)
     *  - springUser: el usuario autenticado en Spring Security
     *
     * Luego redirige de vuelta al detalle del producto, agregando "?reviews"
     * para que la vista sepa recargar la sección de reseñas.
     */
    @PostMapping("/add")
    public String addReview(
        @RequestParam("productoId") Long productoId,
        @RequestParam("comentario") String comentario,
        @RequestParam(value = "rating", required = false) Integer rating,
        @AuthenticationPrincipal User springUser
    ) {
        // 1) Recuperar el producto
        Producto producto = productoService.getById(productoId);
        if (producto == null) {
            throw new IllegalArgumentException("Producto no encontrado: " + productoId);
        }

        // 2) Recuperar el usuario de tu sistema a partir del nombre de usuario de Spring Security
        Usuario usuario = usuarioService
            .findByUsername(springUser.getUsername())
            .orElseThrow(() -> new IllegalArgumentException(
                "Usuario no encontrado: " + springUser.getUsername()
            ));

        // 3) Construir manualmente la entidad Review
        Review review = new Review();
        review.setProducto(producto);
        review.setUsuario(usuario);
        review.setComentario(comentario);
        review.setRating(rating);

        // 4) Guardar la reseña
        reviewService.addReview(review);

        // 5) Redirigir de vuelta al detalle del producto, indicando recarga de reseñas
        return "redirect:/producto/" + productoId + "?reviews";
    }

}
