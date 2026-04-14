package com.majuste.suportetecnico.servicos;

import com.majuste.suportetecnico.model.entidades.Chamado;
import com.majuste.suportetecnico.model.entidades.Mensagem;
import com.majuste.suportetecnico.model.entidades.Usuario;
import com.majuste.suportetecnico.repositorios.MensagemRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MensagemService {

    private final MensagemRepository mensagemRepository;
    private final ChamadoService chamadoService;
    private final UsuarioService usuarioService;

    public MensagemService(MensagemRepository mensagemRepository, ChamadoService chamadoService, UsuarioService usuarioService) {
        this.mensagemRepository = mensagemRepository;
        this.chamadoService = chamadoService;
        this.usuarioService = usuarioService;
    }

    public List<Mensagem> buscarPorChamado(Long id) {return mensagemRepository.findByChamadoId(id);}

    public Mensagem enviar(Long idChamado, Long idUsuario, String texto) {
        Mensagem mensagem = new Mensagem();

        Chamado chamado = chamadoService.buscarPorId(idChamado);
        Usuario usuario = usuarioService.buscarPorId(idUsuario);

        mensagem.setUsuario(usuario);
        mensagem.setChamado(chamado);
        mensagem.setMensagem(texto);
        mensagem.setData(LocalDateTime.now());

        return mensagemRepository.save(mensagem);
    }
}
