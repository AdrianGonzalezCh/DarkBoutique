package com.darkboutique.service.impl;

import com.darkboutique.dao.WishlistDao;
import com.darkboutique.domain.Producto;
import com.darkboutique.domain.Usuario;
import com.darkboutique.domain.Wishlist;
import com.darkboutique.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WishlistServiceImpl implements WishlistService {

    private final WishlistDao wishlistDao;

    @Autowired
    public WishlistServiceImpl(WishlistDao wishlistDao) {
        this.wishlistDao = wishlistDao;
    }

    @Override
    public Wishlist addToWishlist(Usuario usuario, Producto producto) {
        Wishlist entry = new Wishlist();
        entry.setUsuario(usuario);
        entry.setProducto(producto);
        return wishlistDao.save(entry);
    }

    @Override
    public void removeFromWishlist(Long wishlistId) {
        wishlistDao.deleteById(wishlistId);
    }

    @Override
    public List<Wishlist> getWishlistByUsuario(Long usuarioId) {
        return wishlistDao.findByUsuarioId(usuarioId);
    }

    @Override
    public Optional<Wishlist> findById(Long id) {
        return wishlistDao.findById(id);
    }
}
