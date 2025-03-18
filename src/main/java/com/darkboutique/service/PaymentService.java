package com.darkboutique.service;

import com.darkboutique.domain.Orden;

public interface PaymentService {
    boolean processPayment(Orden orden);
}
