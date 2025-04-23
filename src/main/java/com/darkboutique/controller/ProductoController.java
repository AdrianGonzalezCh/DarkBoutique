package com.darkboutique.controller;

import com.darkboutique.domain.Producto;
import com.darkboutique.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping("/productos")
    public String listarProductos(
            @RequestParam(required = false) String categoria,
            Model model) {

        List<Producto> lista;
        if (categoria != null && !categoria.isBlank()) {
            lista = productoService.getByCategoria(categoria);
        } else {
            lista = productoService.getAll();
        }

        model.addAttribute("productos", lista);
        model.addAttribute("categoria", categoria);  // opcional, para conservar la selección
        return "producto/listado";  // Asegúrate de que coincide con tu plantilla
    }

    @GetMapping("/producto/{id}")
    public String detalleProducto(@PathVariable Long id, Model model) {
        Producto p = productoService.getById(id);
        model.addAttribute("producto", p);
        return "producto/detalle";
    }
}
