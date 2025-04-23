package com.darkboutique.service.impl;

import com.darkboutique.domain.CartItem;
import com.darkboutique.service.CartService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de {@link CartService} que almacena el carrito en
 * la sesión HTTP del usuario.
 */
@Service
@SessionScope
public class CartServiceImpl implements CartService {

    /**
     * Lista de ítems en el carrito (almacenada en la sesión).
     */
    private final List<CartItem> items = new ArrayList<>();

    /**
     * Obtiene la lista actual de ítems en el carrito.
     * @return copia de la lista de ítems
     */
    @Override
    public List<CartItem> getItems() {
        return new ArrayList<>(items);
    }

    /**
     * Agrega un ítem al carrito. Si el producto ya existe en el carrito,
     * incrementa su cantidad.
     * @param item ítem a agregar o actualizar
     */
    @Override
    public void addItem(CartItem item) {
        for (CartItem ci : items) {
            if (ci.getProducto().getId().equals(item.getProducto().getId())) {
                ci.setCantidad(ci.getCantidad() + item.getCantidad());
                return;
            }
        }
        items.add(item);
    }

    /**
     * Actualiza la cantidad de un ítem ya presente en el carrito.
     * @param item ítem con la nueva cantidad
     */
    @Override
    public void updateItem(CartItem item) {
        items.stream()
             .filter(ci -> ci.getProducto().getId().equals(item.getProducto().getId()))
             .findFirst()
             .ifPresent(ci -> ci.setCantidad(item.getCantidad()));
    }

    /**
     * Elimina un ítem del carrito por el ID de producto.
     * @param productoId identificador del producto a eliminar
     */
    @Override
    public void removeItem(Long productoId) {
        items.removeIf(ci -> ci.getProducto().getId().equals(productoId));
    }

    /**
     * Vacía todos los ítems del carrito.
     */
    @Override
    public void clear() {
        items.clear();
    }

    /**
     * Calcula el total del carrito (suma de precio * cantidad de cada ítem).
     * Convierte BigDecimal a double antes de multiplicar.
     * @return total del carrito
     */
    @Override
    public double getTotal() {
        return items.stream()
                .mapToDouble(ci -> ci.getProducto().getPrecio().doubleValue() * ci.getCantidad())
                .sum();
    }
}
