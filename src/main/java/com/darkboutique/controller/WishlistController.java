package com.darkboutique.controller;

import com.darkboutique.domain.Producto;
import com.darkboutique.domain.Usuario;
import com.darkboutique.service.ProductoService;
import com.darkboutique.service.UsuarioService;
import com.darkboutique.service.WishlistService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/wishlist")
public class WishlistController {

    private final WishlistService   wishlistService;
    private final UsuarioService    usuarioService;
    private final ProductoService   productoService;

    @Autowired
    public WishlistController(WishlistService wishlistService,
                              UsuarioService usuarioService,
                              ProductoService productoService) {
        this.wishlistService  = wishlistService;
        this.usuarioService   = usuarioService;
        this.productoService  = productoService;
    }

    /**
     * Añade un producto a la lista de deseos del usuario autenticado.
     * Recibe solo productoId y redirige de vuelta a la página anterior.
     */
    @PostMapping("/add")
    public String addToWishlist(@RequestParam("productoId") Long productoId,
                                @AuthenticationPrincipal UserDetails userDetails,
                                HttpServletRequest request,
                                RedirectAttributes attrs) {

        // 1) Obtengo el Usuario actual por su username
        Usuario usuario = usuarioService.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        // 2) Cargo el Producto
        Producto producto = productoService.getById(productoId);
        if (producto == null) {
            throw new IllegalArgumentException("Producto no encontrado: " + productoId);
        }

        // 3) Lo añado a la wishlist
        wishlistService.addToWishlist(usuario, producto);

        // 4) Flash message
        attrs.addFlashAttribute("wishlistMessage",
            "✔ «" + producto.getNombre() + "» añadido a tus Deseados");

        // 5) Redirijo a la página que vino en Referer
        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/");
    }

    /**
     * Elimina un elemento (wishlistId) de la lista de deseos
     * del usuario autenticado, y lo devuelve al listado personal.
     */
    @PostMapping("/remove")
    public String removeFromWishlist(@RequestParam("wishlistId") Long wishlistId,
                                     @AuthenticationPrincipal UserDetails userDetails,
                                     RedirectAttributes attrs) {

        // Elimino
        wishlistService.removeFromWishlist(wishlistId);

        attrs.addFlashAttribute("wishlistMessage",
            "✔ Item eliminado de tus Deseados");

        // Redirijo al listado (ahora no necesito userId en la URL)
        return "redirect:/wishlist/listado";
    }

    /**
     * Muestra el listado actual de deseos para el usuario autenticado.
     */
    @GetMapping("/listado")
    public String listado(@AuthenticationPrincipal UserDetails userDetails,
                          org.springframework.ui.Model model) {

        Usuario usuario = usuarioService.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        model.addAttribute("wishlistItems",
            wishlistService.getWishlistByUsuario(usuario.getId()));
        return "wishlist/listado";
    }
}
