package com.majuste.suportetecnico.repositorios;

import com.majuste.suportetecnico.model.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByEmail(String email);
}
