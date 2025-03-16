
package com.darkboutique.service.impl;

import com.darkboutique.dao.WishlistDao;
import com.darkboutique.domain.Producto;
import com.darkboutique.domain.Usuario;
import com.darkboutique.domain.Wishlist;
import com.darkboutique.service.WishlistService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistServiceImpl implements WishlistService {

    private WishlistDao wishlistDao;

    // Constructor manual para inyectar WishlistDao
    public WishlistServiceImpl(WishlistDao wishlistDao) {
        this.wishlistDao = wishlistDao;
    }

    @Override
    public Wishlist addToWishlist(Usuario usuario, Producto producto) {
        // Creación manual del objeto Wishlist sin usar @Builder
        Wishlist wishlist = new Wishlist();
        wishlist.setUsuario(usuario);
        wishlist.setProducto(producto);
        return wishlistDao.save(wishlist);
    }

    @Override
    public void removeFromWishlist(Long wishlistId) {
        wishlistDao.deleteById(wishlistId);
    }

    @Override
    public List<Wishlist> getWishlistByUsuario(Long usuarioId) {
        return wishlistDao.findByUsuarioId(usuarioId);
    }
}
