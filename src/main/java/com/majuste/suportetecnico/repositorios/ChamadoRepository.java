package com.majuste.suportetecnico.repositorios;

import com.majuste.suportetecnico.model.entidades.Chamado;
import com.majuste.suportetecnico.model.entidades.Mensagem;
import com.majuste.suportetecnico.model.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChamadoRepository extends JpaRepository<Chamado, Long> {
    List<Chamado> findByClienteId(Long clienteId);
}
