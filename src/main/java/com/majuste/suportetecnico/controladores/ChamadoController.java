package com.majuste.suportetecnico.controladores;

import com.majuste.suportetecnico.model.entidades.Chamado;
import com.majuste.suportetecnico.servicos.ChamadoService;
import com.majuste.suportetecnico.servicos.SseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chamados")
@CrossOrigin(origins = "*")
public class ChamadoController {

    //Decorador autowired é uma injeção de dependencias, ele faz a mesma coisa que acontece no service, so que automatico
    @Autowired
    private ChamadoService chamadoService;
    @Autowired
    private SseService sseService;

    //Get para buscar todos os dados, sem o try e catch, se der erro provavelmente o banco caiu dnv
    @GetMapping
    public ResponseEntity<List<Chamado>> buscarTodos() {
        List<Chamado> lista = chamadoService.buscarTodos();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    //Get para buscar por id do chamado
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id, @RequestParam Long idUsuario) {
        try {
            Chamado chamado = chamadoService.buscarPorId(id, idUsuario);
            return ResponseEntity.ok(chamado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }

    //Get para buscar por id do usuario
    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<Chamado>> buscarPorUsuario(@PathVariable Long id) {
        List<Chamado> lista = chamadoService.buscarPorUsuario(id);
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    //Get para buscar por id da categoria
    @GetMapping("/categoria/{id}")
    public ResponseEntity<List<Chamado>> buscarPorCategoria(@PathVariable Long id) {
        List<Chamado> lista = chamadoService.buscarPorCategoria(id);
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    //Post para criar um novo chamado
    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody Chamado chamado, @RequestParam Long idUsuario) {
        try {
            Chamado novoChamado = chamadoService.salvar(chamado, idUsuario);
            return ResponseEntity.status(201).body(novoChamado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    //Patch para atender um chamado
    @PatchMapping("/{id}/atender")
    public ResponseEntity<?> atender(@PathVariable Long id, @RequestParam Long idUsuario) {
        try {
            Chamado chamado = chamadoService.atender(id, idUsuario);
            sseService.notificar(chamado.getCliente().getId(), "CHAMADO_ATENDIDO", chamado);
            sseService.notificar(chamado.getTecnico().getId(), "CHAMADO_ATENDIDO", chamado);
            return ResponseEntity.ok(chamado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }

    //Patch para finalizar um chamado
    @PatchMapping("/{id}/finalizar")
    public ResponseEntity<?> finalizar(@PathVariable Long id, @RequestParam Long idUsuario) {
        try {
            Chamado chamado = chamadoService.finalizar(id, idUsuario);
            sseService.notificar(chamado.getCliente().getId(), "CHAMADO_FINALIZADO", chamado);
            sseService.notificar(chamado.getTecnico().getId(), "CHAMADO_FINALIZADO", chamado);
            return ResponseEntity.ok(chamado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }

    //Delete para remover um chamado
    @DeleteMapping("/{id}/remover")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        chamadoService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
