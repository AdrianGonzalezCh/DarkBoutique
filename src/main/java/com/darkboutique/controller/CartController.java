package com.darkboutique.controller;

import com.darkboutique.domain.CartItem;
import com.darkboutique.domain.Producto;
import com.darkboutique.service.CartService;
import com.darkboutique.service.ProductoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final ProductoService productoService;

    @Autowired
    public CartController(CartService cartService,
                          ProductoService productoService) {
        this.cartService = cartService;
        this.productoService = productoService;
    }

    /**
     * Muestra el carrito de compras.
     */
    @GetMapping
    public String viewCart(Model model) {
        model.addAttribute("items", cartService.getItems());
        model.addAttribute("total", cartService.getTotal());
        return "cart/cart";  // templates/cart/cart.html
    }

    /**
     * Agrega un producto al carrito y redirige de vuelta a la página anterior,
     * mostrando un mensaje flash de confirmación.
     */
    @GetMapping("/add/{id}")
    public String addItem(@PathVariable Long id,
                          HttpServletRequest request,
                          RedirectAttributes attrs) {
        Producto producto = productoService.getById(id);
        cartService.addItem(new CartItem(producto, 1));

        // Mensaje flash que vive solo un redirect
        attrs.addFlashAttribute("cartMessage",
            "✔ “" + producto.getNombre() + "” agregado al carrito");

        // Redirige de vuelta a la página que invocó la acción
        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/productos");
    }

    /**
     * Actualiza la cantidad de un ítem existente en el carrito.
     */
    @PostMapping("/update")
    public String updateItem(@ModelAttribute CartItem item,
                             RedirectAttributes attrs) {
        cartService.updateItem(item);
        attrs.addFlashAttribute("cartMessage",
            "Cantidad de “" + item.getProducto().getNombre() + "” actualizada");
        return "redirect:/cart";
    }

    /**
     * Elimina un ítem del carrito por ID de producto.
     */
    @GetMapping("/remove/{id}")
    public String removeItem(@PathVariable Long id,
                             RedirectAttributes attrs) {
        cartService.removeItem(id);
        attrs.addFlashAttribute("cartMessage",
            "Producto eliminado del carrito");
        return "redirect:/cart";
    }

    /**
     * Vacía todo el carrito.
     */
    @GetMapping("/clear")
    public String clearCart(RedirectAttributes attrs) {
        cartService.clear();
        attrs.addFlashAttribute("cartMessage",
            "El carrito ha sido vaciado");
        return "redirect:/cart";
    }
}
