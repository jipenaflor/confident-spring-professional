package com.romepenaflor.mybank.service;

import com.romepenaflor.mybank.model.Transaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class TransactionService {
    private final String bankSlogan;
    List<Transaction> transactions = new CopyOnWriteArrayList<>();

    public TransactionService(@Value("${bank.slogan}") String bankSlogan) {
        this.bankSlogan = bankSlogan;
    }

    public List<Transaction> findAll() {
        return transactions;
    }

    public List<Transaction> findByReceivingUserId(String userId) {
        return transactions.stream()
                .filter(transaction -> userId.equalsIgnoreCase(transaction.getReceivingUser()))
                .toList();
    }

    public Transaction create(BigDecimal amount, String reference, String receivingUser) {
        ZonedDateTime timestamp = ZonedDateTime.now();
        Transaction transaction = new Transaction(amount, timestamp, reference, bankSlogan, receivingUser);
        transactions.add(transaction);
        return transaction;
    }
}
