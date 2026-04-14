package com.majuste.suportetecnico.controladores;

import com.majuste.suportetecnico.model.entidades.Chamado;
import com.majuste.suportetecnico.servicos.ChamadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chamados")
public class ChamadoController {

    @Autowired
    private ChamadoService chamadoService;

    @GetMapping
    public List<Chamado> buscarTodos() {return chamadoService.buscarTodos();}

    @GetMapping("/{id}")
    public Chamado buscarPorId(@PathVariable Long id) {return chamadoService.buscarPorId(id);}

    @PostMapping("/{idCliente}")
    public Chamado salvar(@RequestBody Chamado chamado, @PathVariable Long idCliente) {return chamadoService.salvar(chamado, idCliente);}

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {chamadoService.remover(id);}

    @PatchMapping("/{id}/atender")
    public Chamado atender(@PathVariable Long id, @RequestParam Long tecnicoId) {return chamadoService.atender(id, tecnicoId);}
    @PatchMapping("/{id}/finalizar")
    public Chamado finalizar(@PathVariable Long id, @RequestParam Long tecnicoId) {return chamadoService.finalizar(id, tecnicoId);}
}
