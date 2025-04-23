package com.darkboutique.controller;

import com.darkboutique.domain.Producto;
import com.darkboutique.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/admin/productos")
public class AdminProductoController {

    private final ProductoService productoService;

    @Autowired
    public AdminProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // Listar todos los productos
    @GetMapping
    public String listarProductos(Model model) {
        model.addAttribute("productos", productoService.getAll());
        return "admin/productos/listado";
    }

    // Mostrar formulario para crear un nuevo producto
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("producto", new Producto());
        return "admin/productos/formulario";
    }

    // Guardar o actualizar un producto
    @PostMapping("/guardar")
    public String guardarProducto(
            @ModelAttribute("producto") Producto producto,
            @RequestParam(value = "imagen", required = false) MultipartFile imagenArchivo) {

        // Manejo de la imagen si se sube
        if (imagenArchivo != null && !imagenArchivo.isEmpty()) {
            String carpetaImagenes = "src/main/resources/static/images/";
            File directorio = new File(carpetaImagenes);
            if (!directorio.exists()) directorio.mkdirs();

            String nombreArchivo = UUID.randomUUID().toString() + "_" + imagenArchivo.getOriginalFilename();
            Path rutaCompleta = Paths.get(carpetaImagenes, nombreArchivo);
            try {
                imagenArchivo.transferTo(rutaCompleta.toFile());
                producto.setImagenUrl("/images/" + nombreArchivo);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        productoService.save(producto);
        return "redirect:/admin/productos";
    }

    // Mostrar formulario para editar producto existente
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Producto producto = productoService.getById(id);
        if (producto == null) {
            return "redirect:/admin/productos";
        }
        model.addAttribute("producto", producto);
        return "admin/productos/formulario";
    }

    // Eliminar un producto
    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        productoService.delete(id);
        return "redirect:/admin/productos";
    }
}
