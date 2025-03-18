package com.darkboutique.controller;

import com.darkboutique.domain.Producto;
import com.darkboutique.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // Listar todos los productos o filtrar por categoría
    @GetMapping("/productos")
    public String listarProductos(@RequestParam(required = false) String categoria, Model model) {
        if (categoria != null && !categoria.isEmpty()) {
            model.addAttribute("productos", productoService.obtenerPorCategoria(categoria));
        } else {
            model.addAttribute("productos", productoService.obtenerTodos());
        }
        return "producto/listado"; // templates/producto/listado.html
    }

    // Mostrar detalle de un producto
    @GetMapping("/producto/{id}")
    public String detalleProducto(@PathVariable Long id, Model model) {
        Producto producto = productoService.obtenerPorId(id).orElse(null);
        model.addAttribute("producto", producto);
        return "producto/detalle"; // templates/producto/detalle.html
    }
}
