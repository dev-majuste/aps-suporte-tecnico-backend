package com.majuste.suportetecnico.servicos;

import com.majuste.suportetecnico.model.entidades.Avaliacao;
import com.majuste.suportetecnico.model.entidades.Chamado;
import com.majuste.suportetecnico.model.entidades.Usuario;
import com.majuste.suportetecnico.model.enums.StatusChamada;
import com.majuste.suportetecnico.repositorios.AvaliacaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AvaliacaoService {

    /*
    AVALIAÇÕES NÃO PODEM SER EXCLUIDAS E NEM EDITADAS
     */

    //Injeções de dependencias e construtor
    private final AvaliacaoRepository avaliacaoRepository;
    private final ChamadoService chamadoService;
    private final UsuarioService usuarioService;

    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository, ChamadoService chamadoService, UsuarioService usuarioService) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.chamadoService = chamadoService;
        this.usuarioService = usuarioService;
    }

    //Metodo para buscar todas as avaliacoes
    public List<Avaliacao> buscarTodos() {return avaliacaoRepository.findAll();}

    //Metodo para buscar avaliacao por id
    public Avaliacao buscarPorId(Long id) {return avaliacaoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Avaliação não encontrada"));}

    //Metodo para buscar a avaliação do chamado
    public Avaliacao buscarPorChamado(Long id) {
        return avaliacaoRepository.findByChamadoId(id)
                .orElseThrow(() -> new RuntimeException("Nao existe avaliacao nesse chamado"));}

    //Metodo para salvar/criar uma avaliação
    public Avaliacao salvar(Long idChamado, Avaliacao avaliacao, Long idCliente) {
        Chamado chamado = chamadoService.buscarPorId(idChamado, idCliente);
        Usuario usuario = usuarioService.buscarPorId(idCliente);

        if (chamado.getStatus() != StatusChamada.RESOLVIDO) {
            throw new RuntimeException("Chamado ainda não resolvido");
        }
        if (avaliacaoRepository.findByChamadoId(idChamado).isPresent()) {
            throw new RuntimeException("Esse chamado ja foi avaliado");
        }
        avaliacao.setChamado(chamado);
        avaliacao.setUsuario(usuario);
        return avaliacaoRepository.save(avaliacao);
    }
}
