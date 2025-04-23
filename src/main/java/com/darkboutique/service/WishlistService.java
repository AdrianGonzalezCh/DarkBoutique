package com.darkboutique.service;

import com.darkboutique.domain.Producto;
import com.darkboutique.domain.Usuario;
import com.darkboutique.domain.Wishlist;

import java.util.List;
import java.util.Optional;

public interface WishlistService {
    /** Añade un producto a la wishlist de un usuario */
    Wishlist addToWishlist(Usuario usuario, Producto producto);
    /** Borra un elemento de la wishlist por su id */
    void removeFromWishlist(Long wishlistId);
    /** Devuelve todos los elementos de wishlist de un usuario */
    List<Wishlist> getWishlistByUsuario(Long usuarioId);
    /** Busca un elemento de wishlist por su id */
    Optional<Wishlist> findById(Long id);
}
