package com.majuste.suportetecnico.controladores;

import com.majuste.suportetecnico.model.entidades.Avaliacao;
import com.majuste.suportetecnico.servicos.AvaliacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chamados")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService avaliacaoService;

    @GetMapping("/{id}/avaliacao")
    public Avaliacao buscarPorId(@PathVariable Long id) {return avaliacaoService.buscarPorChamado(id);}
    @GetMapping("/avaliacoes")
    public List<Avaliacao> buscarTodos() {return avaliacaoService.buscarTodos();}

    @PostMapping("/{id}/avaliacao")
    public Avaliacao salvar(@PathVariable Long id, @RequestBody Avaliacao avaliacao, @RequestParam Long idCliente) {
        return avaliacaoService.salvar(id, avaliacao, idCliente);
    }
}
