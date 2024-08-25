package com.romepenaflor.mybank.service;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

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
    }
}
