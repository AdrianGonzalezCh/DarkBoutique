package com.darkboutique.domain;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "review")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "producto_id")
    private Producto producto;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    
    @Column(columnDefinition = "TEXT")
    private String comentario;
    
    @Column(name = "photo_url")
    private String photoUrl;
    
    private Integer rating;
}
