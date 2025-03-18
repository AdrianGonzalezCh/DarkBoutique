package com.darkboutique.service;

import com.darkboutique.domain.Orden;
import java.util.List;

public interface OrdenService {
    Orden createOrder(Orden orden);
    List<Orden> getOrdersByUsuario(Long usuarioId);
}
