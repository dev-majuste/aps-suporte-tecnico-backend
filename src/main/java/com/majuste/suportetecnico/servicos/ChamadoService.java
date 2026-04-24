package com.majuste.suportetecnico.servicos;

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

    //Injeção de dependencias e construtor
    private final ChamadoRepository chamadoRepository;
    private final CategoriaService categoriaService;
    private final UsuarioService usuarioService;

    public ChamadoService(ChamadoRepository chamadoRepository, CategoriaService categoriaService, UsuarioService usuarioService) {
        this.chamadoRepository = chamadoRepository;
        this.categoriaService = categoriaService;
        this.usuarioService = usuarioService;
    }

    //Metodo para buscar todos os chamados
    public List<Chamado> buscarTodos() {return chamadoRepository.findAll();}

    //Metodo para buscar chamados por cliente
    public List<Chamado> buscarPorUsuario(Long id) {return chamadoRepository.findByClienteId(id);}

    //Metodo para buscar chamados por categoria
    public List<Chamado> buscarPorCategoria(Long id) {return chamadoRepository.findByCategoriaId(id);}

    //Metodo para buscar chamado por id e verifica se o CLIENTE tem acesso a ele
    public Chamado buscarPorId(Long idChamado, Long idUsuario) {
        Chamado chamado = chamadoRepository.findById(idChamado).orElseThrow(() -> new RuntimeException("Chamado não encontrado"));
        Usuario usuario = usuarioService.buscarPorId(idUsuario);
        //Verifica se o usuario é ADMIN ou SUPORTE, ou se o CLIENTE pertence ao chamado
        if(usuario.getCargo() != Cargo.ADMIN && usuario.getCargo() != Cargo.SUPORTE && chamado.getCliente().getId().equals(usuario.getId())) {
            throw new RuntimeException("Acesso negado");
        }
        return chamado;
    }

    //Metodo para criar/salvar um novo chamado
    public Chamado salvar(Chamado chamado, Long idUsuario) {
        Usuario usuario = usuarioService.buscarPorId(idUsuario);
        chamado.setCliente(usuario);
        chamado.setStatus(StatusChamada.EM_ABERTO);
        chamado.setDataCriacao(LocalDate.now());
        return chamadoRepository.save(chamado);
    }

    //Metodo para excluir um chamado
    public void remover(Long id) {chamadoRepository.deleteById(id);}

    //Metodo para um SUPORTE atender um chamado
    public Chamado atender(Long idChamado, Long idSuporte) {
        Chamado chamado = chamadoRepository.findById(idChamado).orElseThrow(() -> new RuntimeException("Chamado não encontrado"));
        Usuario suporte = usuarioService.buscarPorId(idSuporte);
        //Verifica se o status está em aberto
        if (chamado.getStatus() != StatusChamada.EM_ABERTO) {
            throw new RuntimeException("O chamado não esta aberto");
        }
        //Verifica se o usuario é SUPORTE ou ADMIN
        if (suporte.getCargo() == Cargo.CLIENTE) {
            throw new RuntimeException("Sem permissão para atender um chamado");
        }
        chamado.setTecnico(suporte);
        chamado.setStatus(StatusChamada.EM_ANDAMENTO);

        return chamadoRepository.save(chamado);
    }

    //Metodo para finalizar o chamado
    public Chamado finalizar(Long idChamado, Long idSuporte) {
        Chamado chamado = chamadoRepository.findById(idChamado).orElseThrow(() -> new RuntimeException("Chamado não encontrado"));
        Usuario suporte = usuarioService.buscarPorId(idSuporte);
        //Verifica se o SUPORTE do chamado é o mesmo que esta tentando finaliza-lo
        if(!chamado.getTecnico().getId().equals(suporte.getId())) {
            throw new RuntimeException("Apenas o tecnico responsavel pode finalizar o chamado");
        }
        chamado.setStatus(StatusChamada.RESOLVIDO);
        chamado.setDataResolvido(LocalDate.now());
        return chamadoRepository.save(chamado);
    }
}
