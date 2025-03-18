package com.darkboutique.controller;

import com.darkboutique.domain.Orden;
import com.darkboutique.service.OrdenService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/orden")
public class OrdenController {

    private final OrdenService ordenService;

    // Constructor manual para inyectar OrdenService
    public OrdenController(OrdenService ordenService) {
        this.ordenService = ordenService;
    }

    // Mostrar las órdenes realizadas por un usuario
    @GetMapping("/usuario/{usuarioId}")
    public String listOrders(@PathVariable Long usuarioId, Model model) {
        List<Orden> orders = ordenService.getOrdersByUsuario(usuarioId);
        model.addAttribute("orders", orders);
        return "orden/listado"; // Vista en templates/orden/listado.html
    }

    // Crear una nueva orden
    @PostMapping("/create")
    public String createOrder(@ModelAttribute Orden orden) {
        // Se asume que el objeto orden ya contiene los datos necesarios, incluido el usuario.
        Orden nuevaOrden = ordenService.createOrder(orden);
        return "redirect:/orden/usuario/" + nuevaOrden.getUsuario().getId();
    }
}
