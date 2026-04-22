package com.majuste.suportetecnico.servicos;

import com.majuste.suportetecnico.model.entidades.Usuario;
import com.majuste.suportetecnico.model.enums.Cargo;
import com.majuste.suportetecnico.repositorios.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> buscarTodos() {return usuarioRepository.findAll();}

    public Usuario buscarPorId(Long id) {return usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));}

    public Usuario buscarPorEmail(String email) {return usuarioRepository.findByEmail(email);}

    public Usuario salvar(Usuario usuario) {
        usuario.setCargo(Cargo.CLIENTE);
        return usuarioRepository.save(usuario);}

    public Usuario atualizar(Long id, Usuario novoUsuario) {
        Usuario usuario = buscarPorId(id);
        usuario.setEmail(novoUsuario.getEmail());
        usuario.setNome(novoUsuario.getNome());
        usuario.setSenha(novoUsuario.getSenha());
        return usuarioRepository.save(usuario);
    }

    public void remover(Long id) {usuarioRepository.deleteById(id);}

    //Ainda falta o metodo para atualizar o cargo do usuario, lembrar de verificar se o cargo é valido e se o usuario que deu o patch é admin
}
