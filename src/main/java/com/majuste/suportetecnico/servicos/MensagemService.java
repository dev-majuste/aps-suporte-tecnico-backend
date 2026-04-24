package com.majuste.suportetecnico.servicos;

import com.majuste.suportetecnico.model.entidades.Chamado;
import com.majuste.suportetecnico.model.entidades.Mensagem;
import com.majuste.suportetecnico.model.entidades.Usuario;
import com.majuste.suportetecnico.model.enums.Cargo;
import com.majuste.suportetecnico.model.enums.StatusChamada;
import com.majuste.suportetecnico.repositorios.MensagemRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MensagemService {

    //Injeção de dependencias e construtor
    private final MensagemRepository mensagemRepository;
    private final ChamadoService chamadoService;
    private final UsuarioService usuarioService;

    public MensagemService(MensagemRepository mensagemRepository, ChamadoService chamadoService, UsuarioService usuarioService) {
        this.mensagemRepository = mensagemRepository;
        this.chamadoService = chamadoService;
        this.usuarioService = usuarioService;
    }

    //Metodo para buscar todas as mensagens
    public List<Mensagem> buscarTodos() {
        return mensagemRepository.findAll();
    }

    //Metodo para buscar as mensagens por chamado
    public List<Mensagem> buscarPorChamado(Long id) {
        return mensagemRepository.findByChamadoId(id);
    }

    //Metodo para enviar a mensagem
    public Mensagem enviar(Long idChamado, Long idUsuario, String texto) {
        Mensagem msg = new Mensagem();
        Chamado chamado = chamadoService.buscarPorId(idChamado, idUsuario);
        Usuario usuario = usuarioService.buscarPorId(idUsuario);

        if (usuario.getCargo() != Cargo.SUPORTE && chamado.getStatus() == StatusChamada.AGUARDANDO_SUPORTE) {
            throw new RuntimeException("Vez do CLIENTE");
        }
        if (usuario.getCargo() != Cargo.CLIENTE && chamado.getStatus() == StatusChamada.AGUARDANDO_CLIENTE) {
            throw new RuntimeException("Vez do SUPORTE");
        }
        //If ternario para receber o novo status do chamado com base no cargo CLIENTE
        StatusChamada novoStatus = usuario.getCargo() == Cargo.CLIENTE ? StatusChamada.AGUARDANDO_SUPORTE : StatusChamada.AGUARDANDO_CLIENTE;
        chamado.setStatus(novoStatus);
        msg.setUsuario(usuario);
        msg.setChamado(chamado);
        msg.setMensagem(texto);
        msg.setData(LocalDateTime.now());
        return mensagemRepository.save(msg);
    }
}
