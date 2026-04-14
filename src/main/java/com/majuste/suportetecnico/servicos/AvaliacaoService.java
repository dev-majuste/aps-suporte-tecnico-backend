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

    private final AvaliacaoRepository avaliacaoRepository;
    private final ChamadoService chamadoService;
    private final UsuarioService usuarioService;

    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository, ChamadoService chamadoService, UsuarioService usuarioService) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.chamadoService = chamadoService;
        this.usuarioService = usuarioService;
    }

    public List<Avaliacao> buscarTodos() {return avaliacaoRepository.findAll();}

    public Avaliacao buscarPorId(Long id) {return avaliacaoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Avaliação não encontrada"));}

    public Avaliacao buscarPorChamado(Long id) {
        return avaliacaoRepository.findByChamadoId(id).orElseThrow(() -> new RuntimeException("Nao existe avaliacao nesse chamado"));
    }

    public Avaliacao salvar(Long idChamado, Avaliacao avaliacao, Long idCliente) {
        Chamado chamado = chamadoService.buscarPorId(idChamado);
        Usuario cliente = usuarioService.buscarPorId(idCliente);

        if (chamado.getStatus() != StatusChamada.RESOLVIDO) {
            throw new RuntimeException("Você so pode avaliar chamados já resolvidos");
        }
        if (avaliacaoRepository.findByChamadoId(idChamado).isPresent()) {
            throw new RuntimeException("Ja existe uma avaliação nesse chamado");
        }
        if (!Objects.equals(chamado.getCliente().getId(), idChamado)) {
            throw new RuntimeException("Você não pode avaliar chamados de outras pessoas");
        }

        avaliacao.setChamado(chamado);
        avaliacao.setUsuario(cliente);

        return avaliacaoRepository.save(avaliacao);
    }

    /*
    * POR ENQUANTO não tem metodos de atualizar, de patch ou deletar
    * Usuario podera fazer apenas uma avaliação, sem opção de editar
    */
}
