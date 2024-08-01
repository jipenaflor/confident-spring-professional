package com.romepenaflor.mybank.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.UUID;

public class Transaction {

    private String id;
    private BigDecimal amount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mmZ")
    private ZonedDateTime timestamp;
    private String reference;

    public Transaction(BigDecimal amount, ZonedDateTime timestamp, String reference) {
        this.id = UUID.randomUUID().toString();
        this.amount = amount;
        this.timestamp = timestamp;
        this.reference = reference;
    }

    private void setId(String id) {
        this.id = id;
    }
    private String getId() {
        return this.id;
    }

    private void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    private BigDecimal getAmount() {
        return this.amount;
    }

    private void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }
    private ZonedDateTime getTimestamp() {
        return this.timestamp;
    }

    private void setReference(String reference) {
        this.reference = reference;
    }
    private String getReference() {
        return this.reference;
    }
}
