package com.example.ApiInventario.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


import com.example.ApiInventario.Model.Categoria;
import com.example.ApiInventario.Service.CategoriaService;

@RestController
@RequestMapping("api/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

   // GET: obtener todas categorias
    @GetMapping
    public ResponseEntity<List<Categoria>> getAll() {
        return ResponseEntity.ok(categoriaService.getAll());
    }
    // Obtener categoria por ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer categoria_id) {
        Categoria categoria = categoriaService.getById(categoria_id);
        if (categoria != null) {
            return ResponseEntity.ok(categoria);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new z_Mensaje("Persona no encontrada\""));
        }
    }


     // Crear nueva persona (POST)
    @PostMapping
    public ResponseEntity<?> add(@RequestBody Categoria categoria) {
        Categoria nueva = categoriaService.add(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    } 
     @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer categoria_id,@RequestBody Categoria categoria){
        Categoria actualizada = categoriaService.update(categoria_id, categoria);
        if (actualizada != null) {
            return ResponseEntity.ok(actualizada);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoria no encontrada");
        }
    }

    // Eliminar persona (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer categoria_id) {
        Categoria eliminada = categoriaService.delete(categoria_id);
        if (eliminada != null) {
            return ResponseEntity.ok(eliminada);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Persona no encontrada");
        }
    }

    //METODOS HATEOAS

    //METODO HATEOAS para buscar por ID
    @GetMapping("/hateoas/{id}")
    public Categoria obtenerHATEOAS(@PathVariable Integer categoria_id) {
        Categoria cat = categoriaService.getById(categoria_id);
        
        //link HATEOAS para API Gateway "A mano"
        cat.add(Link.of("http://localhost:8888/api/proxy/productos/" + cat.getCategoria_id()).withSelfRel());
        cat.add(Link.of("http://localhost:8888/api/proxy/productos/" + cat.getCategoria_id()).withRel("Modificar HATEOAS").withType("PUT"));
        cat.add(Link.of("http://localhost:8888/api/proxy/productos/" + cat.getCategoria_id()).withRel("Eliminar HATEOAS").withType("DELETE"));

        return cat;
    }

    //METODO HATEOAS para listar todos los productos utilizando HATEOAS
    @GetMapping("/hateoas")
    public List<Categoria> obtenerTodosHATEOAS() {
        List<Categoria> lista = categoriaService.getAll();

        for (Categoria cat : lista) {

            //link HATEOAS para API Gateway "A mano"
            cat.add(Link.of("http://localhost:8888/api/proxy/productos").withRel("Get todos HATEOAS"));
            cat.add(Link.of("http://localhost:8888/api/proxy/productos/" + cat.getCategoria_id()).withRel("Crear HATEOAS").withType("POST"));
        }

        return lista;
    }



}
