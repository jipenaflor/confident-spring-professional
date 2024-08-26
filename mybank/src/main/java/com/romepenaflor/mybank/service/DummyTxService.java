package com.romepenaflor.mybank.service;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Profile("dev")
public class DummyTxService {
    private final TransactionService transactionService;
    public DummyTxService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostConstruct
    public void setup() {
        System.out.println("Creating transactions...");
        transactionService.create(BigDecimal.valueOf(230), "Ellie's treats", "2");
        transactionService.create(BigDecimal.valueOf(170), "Levi's toys", "1");
    }
}
