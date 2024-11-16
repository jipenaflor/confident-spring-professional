package com.jeromepenaflor.mybank.springboot.repository;

import com.jeromepenaflor.mybank.springboot.model.Transaction;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TransactionRepository extends CrudRepository<Transaction, String> {
    Iterable<Transaction> findByReceivingUser(String userId);
}