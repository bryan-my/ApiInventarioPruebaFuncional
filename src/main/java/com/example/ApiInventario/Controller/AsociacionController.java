package com.example.ApiInventario.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ApiInventario.Model.Categoria;
import com.example.ApiInventario.Model.Producto;
import com.example.ApiInventario.Repository.CategoriaRepository;
import com.example.ApiInventario.Repository.ProductoRepository;

@RestController
@RequestMapping("/api/asociaciones")
public class AsociacionController {

     @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;


 @PostMapping("/producto-categoria")
    public ResponseEntity<String> asociarProductoCategoria(
            @RequestParam Integer productoId,
            @RequestParam Integer categoriaId) {
        
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        
        producto.setCategoria(categoria);
        productoRepository.save(producto);
        
        return ResponseEntity.ok("Producto asociado a categoría exitosamente");
    }

}
