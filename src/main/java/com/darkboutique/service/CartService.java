package com.darkboutique.service;

import com.darkboutique.domain.CartItem;
import java.util.List;

public interface CartService {
    /**
     * Obtiene los ítems actualmente en el carrito de la sesión.
     */
    List<CartItem> getItems();

    /**
     * Agrega un nuevo ítem al carrito (o suma cantidad si ya existe).
     */
    void addItem(CartItem item);

    /**
     * Actualiza la cantidad de un ítem existente.
     */
    void updateItem(CartItem item);

    /**
     * Elimina un ítem del carrito por ID de producto.
     */
    void removeItem(Long productoId);

    /**
     * Vacía todo el carrito.
     */
    void clear();

    /**
     * Calcula el total (precio * cantidad) de todos los ítems.
     */
    double getTotal();
}
