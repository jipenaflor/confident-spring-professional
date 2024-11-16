package com.jeromepenaflor.mybank.springboot.service;

import com.jeromepenaflor.mybank.springboot.model.Transaction;

import java.math.BigDecimal;

public interface TransactionService {
    Iterable<Transaction> findAll();
    Iterable<Transaction> findByReceivingUserId(String userId);
    Transaction create(BigDecimal amount, String reference, String receivingUser);
}
