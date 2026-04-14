package com.majuste.suportetecnico.controladores;

import com.majuste.suportetecnico.model.entidades.Categoria;
import com.majuste.suportetecnico.servicos.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public List<Categoria> buscarTodos() {return categoriaService.buscarTodos();}

    @GetMapping("/{id}")
    public Categoria buscarPorId(@PathVariable Long id) {return categoriaService.buscarPorId(id);}

    @PostMapping
    public Categoria salvar(@RequestBody Categoria categoria) {return categoriaService.salvar(categoria);}

    @PutMapping("/{id}")
    public Categoria atualizar(@PathVariable Long id, @RequestBody Categoria categoria) {return categoriaService.atualizar(id, categoria);}

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {categoriaService.remover(id);}
}
