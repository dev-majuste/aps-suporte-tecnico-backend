package com.majuste.suportetecnico.repositorios;

import com.majuste.suportetecnico.model.entidades.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    //Tem nada, pois não há necessidade de buscas adicionais como nos outros repositorios
}
