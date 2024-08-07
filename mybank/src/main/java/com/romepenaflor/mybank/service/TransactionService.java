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

    public List<Transaction> findAll() {
        return transactions;
    }

    public TransactionService(@Value("${bank.slogan}") String bankSlogan) {
        this.bankSlogan = bankSlogan;
    }

    public Transaction create(BigDecimal amount, String reference) {
        ZonedDateTime timestamp = ZonedDateTime.now();
        Transaction transaction = new Transaction(amount, timestamp, reference, bankSlogan);
        transactions.add(transaction);
        return transaction;
    }
}
