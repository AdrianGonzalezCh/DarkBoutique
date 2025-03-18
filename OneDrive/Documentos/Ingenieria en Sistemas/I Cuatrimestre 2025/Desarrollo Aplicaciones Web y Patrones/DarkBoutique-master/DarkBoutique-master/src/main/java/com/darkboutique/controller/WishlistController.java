package com.darkboutique.controller;

import com.darkboutique.domain.Producto;
import com.darkboutique.domain.Usuario;
import com.darkboutique.domain.Wishlist;
import com.darkboutique.service.WishlistService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/wishlist")
public class WishlistController {

    private WishlistService wishlistService;

    // Constructor manual para inyectar WishlistService
    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    // Mostrar la lista de deseos de un usuario
    @GetMapping("/{usuarioId}")
    public String getWishlist(@PathVariable Long usuarioId, Model model) {
        List<Wishlist> wishlistItems = wishlistService.getWishlistByUsuario(usuarioId);
        model.addAttribute("wishlistItems", wishlistItems);
        return "wishlist/listado"; // Vista en templates/wishlist/listado.html
    }

    // Agregar un producto a la lista de deseos
    @PostMapping("/add")
    public String addToWishlist(@RequestParam Long usuarioId, @RequestParam Long productoId) {
        // Para simplificar, se crean objetos Usuario y Producto solo con su id.
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        Producto producto = new Producto();
        producto.setId(productoId);
        wishlistService.addToWishlist(usuario, producto);
        return "redirect:/wishlist/" + usuarioId;
    }

    // Eliminar un elemento de la lista de deseos
    @PostMapping("/remove")
    public String removeFromWishlist(@RequestParam Long wishlistId, @RequestParam Long usuarioId) {
        wishlistService.removeFromWishlist(wishlistId);
        return "redirect:/wishlist/" + usuarioId;
    }
}
