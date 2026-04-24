package com.majuste.suportetecnico.repositorios;

import com.majuste.suportetecnico.model.entidades.Chamado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChamadoRepository extends JpaRepository<Chamado, Long> {
    List<Chamado> findByClienteId(Long clienteId); //Busca um chamado pelo id do cliente
    List<Chamado> findByCategoriaId(Long categoriaId); //Busca um chamado pelo id da categoria
}
