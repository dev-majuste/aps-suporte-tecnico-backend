package com.majuste.suportetecnico.servicos;

import com.majuste.suportetecnico.model.entidades.Categoria;
import com.majuste.suportetecnico.repositorios.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> buscarTodos() {return categoriaRepository.findAll();}

    public Categoria buscarPorId(Long id) {return categoriaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));}

    public Categoria salvar(Categoria categoria) {return categoriaRepository.save(categoria);}

    public Categoria atualizar(Long id, Categoria novaCategoria) {
        Categoria categoria = buscarPorId(id);

        categoria.setNome(novaCategoria.getNome());
        categoria.setDescricao(novaCategoria.getDescricao());

        return salvar(categoria);
    }

    public void remover(Long id) {categoriaRepository.deleteById(id);}
}
