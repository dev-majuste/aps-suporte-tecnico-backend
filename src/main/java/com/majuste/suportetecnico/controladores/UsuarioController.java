package com.majuste.suportetecnico.controladores;

import com.majuste.suportetecnico.model.entidades.Usuario;
import com.majuste.suportetecnico.model.enums.Cargo;
import com.majuste.suportetecnico.servicos.SseService;
import com.majuste.suportetecnico.servicos.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    //Injeção de dependencia e construtor com o decorator autowired
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private SseService sseService;

    //Get para buscar todos os usuarios
    @GetMapping
    public ResponseEntity<List<Usuario>> buscarTodos() {
        List<Usuario> lista = usuarioService.buscarTodos();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    //Get para buscar o usuario pelo id
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.buscarPorId(id);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //Post para salvar/criar um usuario
    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody Usuario usuario) {
        try {
            Usuario novoUsuario = usuarioService.salvar(usuario);
            return ResponseEntity.status(201).body(novoUsuario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    //Post para fazer login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario loginInput) {
        try {
            Usuario usuario = usuarioService.buscarPorEmail(loginInput.getEmail());
            if (usuario == null || !usuario.getSenha().equals(loginInput.getSenha())) {
                return ResponseEntity.status(401).body("Email ou senha incorretos");
            }
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    //Put para atualizar os dados de um usuario
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
        try {
            Usuario novoUsuario = usuarioService.atualizarDados(id, usuario);
            sseService.notificar(novoUsuario.getId(), "USUARIO_ATUALIZADO", novoUsuario);
            return ResponseEntity.ok(novoUsuario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    //Patch para trocar o cargo de um usuario
    @PatchMapping("/{id}")
    public ResponseEntity<?> atualizarCargo(@PathVariable Long id, @RequestBody Cargo cargo, @RequestParam Long idAdmin) {
        try {
            Usuario usuario = usuarioService.atualizarCargo(id, cargo, idAdmin);
            sseService.notificar(id, "CARGO_ATUALIZADO", usuario);
            sseService.notificar(idAdmin, "CARGO_ATUALIZADO", usuario);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    //Delete para remover um usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        usuarioService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
