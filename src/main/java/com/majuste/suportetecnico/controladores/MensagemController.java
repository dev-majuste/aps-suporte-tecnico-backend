package com.majuste.suportetecnico.controladores;

import com.majuste.suportetecnico.model.entidades.Chamado;
import com.majuste.suportetecnico.model.entidades.Mensagem;
import com.majuste.suportetecnico.servicos.ChamadoService;
import com.majuste.suportetecnico.servicos.MensagemService;
import com.majuste.suportetecnico.servicos.SseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chamados")
@CrossOrigin(origins = "*")
public class MensagemController {

    //Injeção de dependencias e construtor com decorador autowired
    @Autowired
    private MensagemService mensagemService;
    @Autowired
    private SseService sseService;
    @Autowired
    private ChamadoService chamadoService;

    //Get para buscar as mensagens de um chamado
    @GetMapping("/{id}/mensagens")
    public ResponseEntity<List<Mensagem>> buscarPorChamado(@PathVariable Long id) {
        List<Mensagem> lista = mensagemService.buscarPorChamado(id);
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }
    //Post para enviar uma mensagem
    @PostMapping("/{id}/mensagens")
    public ResponseEntity<?> enviar(@PathVariable Long id, @RequestBody Mensagem mensagem, @RequestParam Long idUsuario) {
        try {
            Chamado chamado = chamadoService.buscarPorId(id, idUsuario);
            Mensagem msg = mensagemService.enviar(id, idUsuario, mensagem.getMensagem());
            sseService.notificar(chamado.getCliente().getId(), "NOVA_MENSAGEM", msg);
            sseService.notificar(chamado.getTecnico().getId(), "NOVA_MENSAGEM", msg);
            return ResponseEntity.status(201).body(msg);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}
