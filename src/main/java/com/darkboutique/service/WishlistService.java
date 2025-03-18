package com.darkboutique.service;

import com.darkboutique.domain.Producto;
import com.darkboutique.domain.Usuario;
import com.darkboutique.domain.Wishlist;
import java.util.List;

public interface WishlistService {
    Wishlist addToWishlist(Usuario usuario, Producto producto);
    void removeFromWishlist(Long wishlistId);
    List<Wishlist> getWishlistByUsuario(Long usuarioId);
}
