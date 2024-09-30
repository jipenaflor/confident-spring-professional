package com.jeromepenaflor.mybank.springboot.web;

import com.jeromepenaflor.mybank.springboot.dto.TransactionDto;
import com.jeromepenaflor.mybank.springboot.model.Transaction;
import com.jeromepenaflor.mybank.springboot.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TransactionController {
    private final TransactionService transactionService;
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/transactions")
    public List<Transaction> findAll() {
        return transactionService.findAll();
    }

    @PostMapping("/transactions")
    public Transaction createTransaction(@RequestBody @Valid TransactionDto transactionDto) {
        return transactionService.create(transactionDto.getAmount(),
                transactionDto.getReference(), transactionDto.getReceivingUser());
    }

}
