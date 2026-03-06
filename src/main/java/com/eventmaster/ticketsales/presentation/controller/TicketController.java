package com.eventmaster.ticketsales.presentation.controller;

import com.eventmaster.ticketsales.application.dto.PurchaseRequest;
import com.eventmaster.ticketsales.application.dto.PurchaseResponse;
import com.eventmaster.ticketsales.application.usecase.PurchaseTicketUseCase;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller REST para operações de venda de ingressos.
 * Validação de inputs via @Valid (proteção OWASP - Input Validation).
 */
@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final PurchaseTicketUseCase purchaseTicketUseCase;

    public TicketController(PurchaseTicketUseCase purchaseTicketUseCase) {
        this.purchaseTicketUseCase = purchaseTicketUseCase;
    }

    @PostMapping("/purchase")
    public ResponseEntity<PurchaseResponse> purchase(@Valid @RequestBody PurchaseRequest request) {
        PurchaseResponse response = purchaseTicketUseCase.execute(request);
        return ResponseEntity.ok(response);
    }
}
