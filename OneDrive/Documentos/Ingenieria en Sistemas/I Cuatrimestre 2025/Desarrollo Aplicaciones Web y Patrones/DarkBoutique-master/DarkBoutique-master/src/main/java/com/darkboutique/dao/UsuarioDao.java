package com.darkboutique.dao;

import com.darkboutique.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioDao extends JpaRepository<Usuario, Long> {
    // Aquí puedes agregar métodos de consulta personalizados si lo requieres.
}
