package com.majuste.suportetecnico.servicos;

import com.majuste.suportetecnico.model.entidades.Usuario;
import com.majuste.suportetecnico.model.enums.Cargo;
import com.majuste.suportetecnico.repositorios.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    //Injeção de dependencia no construtor
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    //Metodo para buscar todos os usuarios
    public List<Usuario> buscarTodos() {return usuarioRepository.findAll();}

    //Metodo para buscar usuario por id
    public Usuario buscarPorId(Long id) {return usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));} //orElseThrow() é do Optional

    //Metodo para buscar usuario por email
    public Usuario buscarPorEmail(String email) {return usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));}

    //Metodo para criar/salvar um novo usuario
    public Usuario salvar(Usuario usuario) {
        boolean verificador = emailExiste(usuario.getEmail());
        if (verificador) {
            throw new RuntimeException("Este email ja foi utilizado");
        }
        usuario.setCargo(Cargo.CLIENTE); //Ja seta o cargo de CLIENTE por padrao
        return usuarioRepository.save(usuario);
    }

    //Metodo para atualizar dados de um usuario
    public Usuario atualizarDados(Long idUsuario, Usuario novoUsuario) {
        Usuario usuario = buscarPorId(idUsuario);
        //Verifica se o email ja esta sendo utilizado por outro usuario
        if (emailExiste(novoUsuario.getEmail()) && !novoUsuario.getEmail().equals(usuario.getEmail())) {
            throw new RuntimeException("Email ja esta sendo utilizado por outro usuario");
        }

        usuario.setEmail(novoUsuario.getEmail());
        usuario.setNome(novoUsuario.getNome());
        usuario.setSenha(novoUsuario.getSenha());
        return usuarioRepository.save(usuario);
    }
    //Metodo para atualizar o cargo de um usuario (metodo exclusivo de admin)
    public Usuario atualizarCargo(Long idUsuario, Cargo novoCargo, Long idAdmin) {
        Usuario usuario = buscarPorId(idUsuario);
        Usuario admin = buscarPorId(idAdmin);
        //Verifica se o usuario que mandou a requisição para trocar o cargo de outro usuario é um ADMIN
        if (admin.getCargo() != Cargo.ADMIN){
            return null;
        }
        usuario.setCargo(novoCargo);
        return usuarioRepository.save(usuario);
    }

    //Metodo para excluir um usuario
    public void remover(Long id) {usuarioRepository.deleteById(id);}

    //Metodo para verificar se o email ja foi utilizado
    public boolean emailExiste(String email) { return usuarioRepository.existsByEmail(email);}
}
