package com.darkboutique.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartController {

    @GetMapping("/cart")
    public String cart(Model model) {
        // Agregar atributos al modelo según sea necesario
        return "cart/cart"; // Esto debe apuntar a src/main/resources/templates/cart/cart.html
    }
}
