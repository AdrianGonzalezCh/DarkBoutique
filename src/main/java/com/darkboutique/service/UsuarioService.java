package com.darkboutique.service;

import com.darkboutique.domain.Usuario;
import java.util.Optional;

/**
 * Servicio para operaciones sobre {@link Usuario}.
 */
public interface UsuarioService {

    /**
     * Busca un usuario por su nombre de usuario.
     *
     * @param username el nombre de usuario a buscar
     * @return un {@link Optional} con el Usuario si existe, o vacío en caso contrario
     */
    Optional<Usuario> findByUsername(String username);

    /**
     * Busca un usuario por su ID.
     *
     * @param id el identificador del usuario
     * @return un {@link Optional} con el Usuario si existe, o vacío en caso contrario
     */
    Optional<Usuario> findById(Long id);

    /**
     * Guarda (o actualiza) un usuario en la base de datos.
     *
     * @param usuario el usuario a guardar
     * @return el usuario guardado
     */
    Usuario save(Usuario usuario);
}
