package com.darkboutique.domain;

import jakarta.persistence.*;

/**
 * Entidad que representa un rol de usuario en la aplicación.
 */
@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre del rol, por ejemplo "ROLE_USER", "ROLE_ADMIN".
     */
    @Column(nullable = false, unique = true)
    private String name;

    /** Constructor vacío requerido por JPA */
    public Role() { }

    /**
     * Constructor completo.
     * @param id   Identificador (puede ser null al crear)
     * @param name Nombre del rol
     */
    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters y setters

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
