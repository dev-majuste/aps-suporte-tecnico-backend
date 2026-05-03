package com.majuste.suportetecnico.controladores;

import com.majuste.suportetecnico.model.entidades.Avaliacao;
import com.majuste.suportetecnico.model.entidades.Chamado;
import com.majuste.suportetecnico.servicos.AvaliacaoService;
import com.majuste.suportetecnico.servicos.ChamadoService;
import com.majuste.suportetecnico.servicos.SseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chamados")
@CrossOrigin(origins = "*")
public class AvaliacaoController {

    //Injeção de dependencia com decorator autowired
    @Autowired
    private AvaliacaoService avaliacaoService;
    @Autowired
    private ChamadoService chamadoService;
    @Autowired
    private SseService sseService;

    //Get de todas as avaliações
    @GetMapping("/avaliacoes")
    public ResponseEntity<List<Avaliacao>> buscarTodos() {
        List<Avaliacao> lista = avaliacaoService.buscarTodos();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    //Get por meio do id do chamado
    @GetMapping("/{id}/avaliacao")
    public ResponseEntity<Avaliacao> buscarPorId(@PathVariable Long id) {
        try {
            Avaliacao avaliacao = avaliacaoService.buscarPorChamado(id);
            return ResponseEntity.ok(avaliacao);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //Post para o usuario poder avaliar
    @PostMapping("/{id}/avaliar")
    public ResponseEntity<?> salvar(@PathVariable Long id, @RequestBody Avaliacao avaliacao, @RequestParam Long idUsuario) {
        try {
            Avaliacao ava = avaliacaoService.salvar(id, avaliacao, idUsuario);
            Chamado chamado = chamadoService.buscarPorId(id, idUsuario);
            sseService.notificar(chamado.getCliente().getId(), "CHAMADO_AVALIADO", ava);
            sseService.notificar(chamado.getTecnico().getId(), "CHAMADO_AVALIADO", ava);
            return ResponseEntity.status(201).body(ava);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}