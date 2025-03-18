package com.darkboutique.service.impl;

import com.darkboutique.domain.Orden;
import com.darkboutique.service.PaymentService;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Override
    public boolean processPayment(Orden orden) {
        // Aquí se implementaría la lógica de integración con un proveedor de pagos.
        // Este ejemplo simula un pago exitoso.
        return true;
    }
}
