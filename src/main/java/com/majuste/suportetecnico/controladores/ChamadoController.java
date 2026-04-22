package com.majuste.suportetecnico.controladores;

import com.majuste.suportetecnico.model.entidades.Chamado;
import com.majuste.suportetecnico.servicos.ChamadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chamados")
@CrossOrigin(origins = "*")
public class ChamadoController {

    @Autowired
    private ChamadoService chamadoService;

    @GetMapping
    public List<Chamado> buscarTodos() {return chamadoService.buscarTodos();}

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id, @RequestParam Long userId) {
        try {
            // Agora o Service recebe o id do chamado e o id de quem está logado
            Chamado chamado = chamadoService.buscarPorId(id, userId);
            return ResponseEntity.ok(chamado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }

    @GetMapping("/usuario/{id}")
    public List<Chamado> buscarPorUsuarioId(@PathVariable Long id) {
        return chamadoService.buscarPorUserId(id);
    }

    @PostMapping()
    public Chamado salvar(@RequestBody Chamado chamado, @RequestParam Long idCliente) {return chamadoService.salvar(chamado, idCliente);}

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {chamadoService.remover(id);}

    @PatchMapping("/{id}/atender")
    public Chamado atender(@PathVariable Long id, @RequestParam Long tecnicoId) {return chamadoService.atender(id, tecnicoId);}
    @PatchMapping("/{id}/finalizar")
    public Chamado finalizar(@PathVariable Long id, @RequestParam Long tecnicoId) {return chamadoService.finalizar(id, tecnicoId);}
}
