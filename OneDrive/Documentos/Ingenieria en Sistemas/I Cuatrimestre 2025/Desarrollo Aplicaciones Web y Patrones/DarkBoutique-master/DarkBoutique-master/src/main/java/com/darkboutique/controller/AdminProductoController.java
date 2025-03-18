package com.darkboutique.controller;

import com.darkboutique.domain.Producto;
import com.darkboutique.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
@RequestMapping("/admin/productos") // Ruta base para productos
public class AdminProductoController {

    private final ProductoService productoService;

    public AdminProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // Mostrar lista de productos
    @GetMapping
    public String listarProductos(Model model) {
        model.addAttribute("productos", productoService.obtenerTodos());
        return "admin/productos/listado"; // Vista en templates/admin/productos/listado.html
    }

    // Mostrar formulario de nuevo producto
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("producto", new Producto());
        return "admin/productos/formulario";
    }

    // Guardar o actualizar producto
    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute Producto producto,
                                  @RequestParam("imagen") MultipartFile imagenArchivo) {
        // Manejo de la imagen si se sube
        if (!imagenArchivo.isEmpty()) {
            String carpetaImagenes = "src/main/resources/static/images/";
            File directorio = new File(carpetaImagenes);
            if (!directorio.exists()) {
                directorio.mkdirs();
            }
            String nombreArchivo = UUID.randomUUID().toString() + "_" + imagenArchivo.getOriginalFilename();
            Path rutaCompleta = Paths.get(carpetaImagenes + nombreArchivo);
            try {
                imagenArchivo.transferTo(rutaCompleta.toFile());
                producto.setImagenUrl("/images/" + nombreArchivo);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        productoService.guardar(producto);
        return "redirect:/admin/productos";
    }

    // Editar producto
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Producto producto = productoService.obtenerPorId(id).orElse(null);
        model.addAttribute("producto", producto);
        return "admin/productos/formulario";
    }

    // Eliminar producto
    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        productoService.eliminar(id);
        return "redirect:/admin/productos";
    }
}
