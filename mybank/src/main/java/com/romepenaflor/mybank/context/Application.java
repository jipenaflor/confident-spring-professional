package com.romepenaflor.mybank.context;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.romepenaflor.mybank.service.TransactionService;

public class Application {
    public static final TransactionService transactionService = new TransactionService();
    public static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

}
