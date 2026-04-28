package com.majuste.suportetecnico.repositorios;

import com.majuste.suportetecnico.model.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//O JpaRepository fornece os metodos prontos do CRUD pro banco de dados
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email); //Busca o usuario pelo email
    boolean existsByEmail(String email); //Verifica se ja existe um usuario com este email
}
