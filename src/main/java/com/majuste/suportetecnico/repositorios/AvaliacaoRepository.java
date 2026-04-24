package com.majuste.suportetecnico.repositorios;

import com.majuste.suportetecnico.model.entidades.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
    //O Optional<T> significa que pode ser null, exemplo: Buscar a avaliação de um chamado que ainda nao foi avaliado retornaria como null
    Optional<Avaliacao> findByChamadoId(Long chamadoId); //Busca uma avaliação pelo id do chamado
}
