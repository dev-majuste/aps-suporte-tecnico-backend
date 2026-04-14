package com.majuste.suportetecnico.controladores;

import com.majuste.suportetecnico.model.entidades.Usuario;
import com.majuste.suportetecnico.servicos.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<Usuario> buscarTodos() {return usuarioService.buscarTodos();}

    @GetMapping("/{id}")
    public Usuario buscarPorId(@PathVariable Long id) {return usuarioService.buscarPorId(id);}

    @PostMapping
    public Usuario salvar(@RequestBody Usuario usuario) {return usuarioService.salvar(usuario);}

    @PutMapping("/{id}")
    public Usuario atualizar(@PathVariable Long id, @RequestBody Usuario usuario) {return usuarioService.atualizar(id, usuario);}

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {usuarioService.remover(id);}
}
