package com.majuste.suportetecnico.repositorios;

import com.majuste.suportetecnico.model.entidades.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
    Optional<Avaliacao> findByChamadoId(Long chamadoId);
}
