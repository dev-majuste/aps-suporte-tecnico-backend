package com.majuste.suportetecnico.controladores;

import com.majuste.suportetecnico.model.entidades.Categoria;
import com.majuste.suportetecnico.servicos.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = "*")
public class CategoriaController {

    //Injeção de dependencias com decorator autowired
    @Autowired
    private CategoriaService categoriaService;

    //Get para todas as categorias
    @GetMapping
    public ResponseEntity<List<Categoria>> buscarTodos() {
        List<Categoria> lista = categoriaService.buscarTodos();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    //Get para buscar a categoria pelo id
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            Categoria categoria = categoriaService.buscarPorId(id);
            return ResponseEntity.ok(categoria);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    //Post para criar uma nova categoria
    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody Categoria categoria) {
        try {
            Categoria novCategoria = categoriaService.salvar(categoria);
            return ResponseEntity.status(201).body(novCategoria);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    //Put para atualizar uma categoria
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Categoria categoria) {
        try {
            Categoria novaCategoria = categoriaService.atualizar(id, categoria);
            return ResponseEntity.ok(novaCategoria);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    //Delete para remover uma categoria
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        categoriaService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
