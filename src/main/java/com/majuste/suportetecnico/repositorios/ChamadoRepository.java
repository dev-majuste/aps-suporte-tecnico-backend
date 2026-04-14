package com.majuste.suportetecnico.repositorios;

import com.majuste.suportetecnico.model.entidades.Chamado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChamadoRepository extends JpaRepository<Chamado, Long> {
}
