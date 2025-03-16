package com.darkboutique.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "orden")
public class Orden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(name = "tracking_status")
    private String trackingStatus;

    private String address;

    @Column(name = "payment_method")
    private String paymentMethod;

    private BigDecimal total;

    // Constructor sin parámetros
    public Orden() {
    }

    // Constructor con todos los parámetros
    public Orden(Long id, Usuario usuario, String trackingStatus, String address, String paymentMethod, BigDecimal total) {
        this.id = id;
        this.usuario = usuario;
        this.trackingStatus = trackingStatus;
        this.address = address;
        this.paymentMethod = paymentMethod;
        this.total = total;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getTrackingStatus() {
        return trackingStatus;
    }

    public void setTrackingStatus(String trackingStatus) {
        this.trackingStatus = trackingStatus;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
