package com.majuste.suportetecnico.servicos;

import com.majuste.suportetecnico.model.entidades.Categoria;
import com.majuste.suportetecnico.repositorios.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    //Injeçoes de dependencia e construtor
    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    //Metodo para buscar todas as categorias
    public List<Categoria> buscarTodos() {return categoriaRepository.findAll();}

    //Metodo para buscar a categoria por id
    public Categoria buscarPorId(Long id) {return categoriaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));}

    //Metodo para salvar/criar uma categoria
    public Categoria salvar(Categoria categoria) {return categoriaRepository.save(categoria);}

    //Metodo para atualizar uma categoria
    public Categoria atualizar(Long idCategoria, Categoria novaCategoria) {
        Categoria categoria = categoriaRepository.findById(idCategoria).orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
        categoria.setNome(novaCategoria.getNome());
        categoria.setDescricao(novaCategoria.getDescricao());

        return categoriaRepository.save(categoria);}

    //Metodo para excluir uma categoria
    public void remover(Long id) {categoriaRepository.deleteById(id);}
}
