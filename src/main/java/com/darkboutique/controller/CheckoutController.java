package com.darkboutique.controller;

import com.darkboutique.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para el flujo de checkout.
 */
@Controller
@RequestMapping("/checkout")
public class CheckoutController {

    private final CartService cartService;

    @Autowired
    public CheckoutController(CartService cartService) {
        this.cartService = cartService;
    }

    /**
     * Muestra el formulario de checkout con los ítems del carrito.
     */
    @GetMapping
    public String showCheckout(Model model) {
        model.addAttribute("items", cartService.getItems());
        model.addAttribute("total", cartService.getTotal());
        return "checkout/checkout";  // templates/checkout/checkout.html
    }

    /**
     * Procesa el pago (simulado), limpia el carrito y redirige a confirmación.
     */
    @PostMapping("/process")
    public String processCheckout(@RequestParam String address,
                                  @RequestParam String paymentMethod,
                                  Model model) {
        // Aquí podrías integrar tu pasarela de pago...
        // Por ahora simulamos éxito:
        model.addAttribute("address", address);
        model.addAttribute("paymentMethod", paymentMethod);
        model.addAttribute("items", cartService.getItems());
        model.addAttribute("total", cartService.getTotal());
        cartService.clear();
        return "checkout/confirmation"; // templates/checkout/confirmation.html
    }
}
