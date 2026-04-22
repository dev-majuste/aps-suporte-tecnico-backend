package com.majuste.suportetecnico.servicos;

import com.majuste.suportetecnico.model.entidades.Categoria;
import com.majuste.suportetecnico.model.entidades.Chamado;
import com.majuste.suportetecnico.model.entidades.Usuario;
import com.majuste.suportetecnico.model.enums.Cargo;
import com.majuste.suportetecnico.model.enums.StatusChamada;
import com.majuste.suportetecnico.repositorios.ChamadoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ChamadoService {

    private final ChamadoRepository chamadoRepository;
    private final CategoriaService categoriaService;
    private final UsuarioService usuarioService;

    public ChamadoService(ChamadoRepository chamadoRepository, CategoriaService categoriaService, UsuarioService usuarioService) {
        this.chamadoRepository = chamadoRepository;
        this.categoriaService = categoriaService;
        this.usuarioService = usuarioService;
    }

    public List<Chamado> buscarTodos() {return chamadoRepository.findAll();}

    public List<Chamado> buscarPorUserId(Long id) {
        return chamadoRepository.findByClienteId(id);
    }

    public Chamado buscarPorId(Long id, Long userId) {
        Chamado chamado = chamadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chamado não encontrado"));
        Usuario usuario = usuarioService.buscarPorId(userId);
        if (!usuario.getCargo().equals(Cargo.ADMIN) && !usuario.getCargo().equals(Cargo.SUPORTE) && !chamado.getCliente().getId().equals(userId)) {
            throw new RuntimeException("Acesso negado: Este chamado não pertence a você!");
        }
        return chamado;}

    public Chamado salvar(Chamado chamadoRest, Long idCliente) {
        Chamado chamado = new Chamado();

        chamado.setTitulo(chamadoRest.getTitulo());
        chamado.setDescricao(chamadoRest.getDescricao());

        Categoria categoria = categoriaService.buscarPorId(chamadoRest.getCategoria().getId());
        chamado.setCategoria(categoria);

        Usuario cliente = usuarioService.buscarPorId(idCliente);
        chamado.setCliente(cliente);

        chamado.setStatus(StatusChamada.EM_ABERTO);
        chamado.setDataCriacao(LocalDate.now());

        return chamadoRepository.save(chamado);
    }

    public void remover(Long id) {chamadoRepository.deleteById(id);}

    public Chamado atender(Long id, Long tecnicoId) {
        Chamado chamado = buscarPorId(id, tecnicoId);

        Usuario tecnico = usuarioService.buscarPorId(tecnicoId);

        if(chamado.getStatus() != StatusChamada.EM_ABERTO) {
            throw new RuntimeException("Chamado não esta aberto");
        }
        //Ainda falta colocar a verificação se o usuario é suporte

        chamado.setTecnico(tecnico);
        chamado.setStatus(StatusChamada.EM_ANDAMENTO);

        return chamadoRepository.save(chamado);
    }

    public Chamado finalizar(Long id, Long tecnicoId) {
        Chamado chamado = buscarPorId(id, tecnicoId);

        Usuario tecnico = usuarioService.buscarPorId(tecnicoId);

        if(chamado.getTecnico() == null || !chamado.getTecnico().getId().equals(tecnico.getId())) {
            throw new RuntimeException("Apenas o tecnico responsavel pode finalizar o chamado");
        }
        if (chamado.getStatus() != StatusChamada.EM_ANDAMENTO) {
            throw new RuntimeException("O chamado não está em andamento");
        }
        chamado.setStatus(StatusChamada.RESOLVIDO);
        chamado.setDataResolvido(LocalDate.now());
        return chamadoRepository.save(chamado);
    }
}
