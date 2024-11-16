package com.jeromepenaflor.mybank.springboot.service;

import com.jeromepenaflor.mybank.springboot.model.Transaction;
import com.jeromepenaflor.mybank.springboot.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class TransactionServiceJdbc implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final String bankSlogan;
    private static final ZoneId DEFAULT_ZONE = ZoneId.systemDefault();

    public TransactionServiceJdbc(TransactionRepository transactionRepository, @Value("${bank.slogan}") String bankSlogan) {
        this.transactionRepository = transactionRepository;
        this.bankSlogan = bankSlogan;
    }

    public Iterable<Transaction> findAll() {
        return transactionRepository.findAll();
    }
    public Iterable<Transaction> findByReceivingUserId(String userId) {
        return transactionRepository.findByReceivingUser(userId);
    }
    @Transactional
    public Transaction create(BigDecimal amount, String reference, String receivingUser) {
        ZonedDateTime timeStamp =  ZonedDateTime.now();
        Transaction tx = new Transaction(amount, timeStamp, reference, bankSlogan, receivingUser);
        transactionRepository.save(tx);
        return tx;
    }
}
