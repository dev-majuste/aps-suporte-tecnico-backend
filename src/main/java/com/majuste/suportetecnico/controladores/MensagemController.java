package com.majuste.suportetecnico.controladores;

import com.majuste.suportetecnico.model.entidades.Mensagem;
import com.majuste.suportetecnico.servicos.MensagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chamados")
public class MensagemController {

    @Autowired
    private MensagemService mensagemService;

    @GetMapping("/{id}/mensagens")
    public List<Mensagem> buscarPorChamado(@PathVariable Long id) {return mensagemService.buscarPorChamado(id);}

    @PostMapping("/{id}/mensagens") //Id referente ao chamado
    public Mensagem enviar(@PathVariable Long id, @RequestBody Mensagem mensagem, @RequestParam Long idUsuario) {return mensagemService.enviar(id, idUsuario, mensagem.getMensagem());}


}
