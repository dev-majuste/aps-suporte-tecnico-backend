package com.majuste.suportetecnico.repositorios;

import com.majuste.suportetecnico.model.entidades.Mensagem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MensagemRepository extends JpaRepository<Mensagem, Long> {
    List<Mensagem> findByChamadoId(Long chamadoId); //Aqui o spring reconhece como Chamado(Nome da classe) + Id(Atributo) para buscar no banco de dados, SE INVERTER O NOME É MAIS 15MIN TENTANDO DESCOBRIR OQ DEU DE ERRO
}
