package com.jeromepenaflor.mybank.springboot.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Table("transactions")
public class Transaction {
    @Id
    private String id;
    private BigDecimal amount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mmZ")
    private ZonedDateTime timestamp;
    private String reference;
    @JsonProperty("bank_slogan")
    private String bankSlogan;
    @JsonProperty("receiving_user")
    private String receivingUser;

    public Transaction() {
    }

    public Transaction(BigDecimal amount, ZonedDateTime timestamp, String reference, String bankSlogan, String receivingUser) {
        this.amount = amount;
        this.timestamp = timestamp;
        this.reference = reference;
        this.bankSlogan = bankSlogan;
        this.receivingUser = receivingUser;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getBankSlogan() {
        return bankSlogan;
    }

    public void setBankSlogan(String bankSlogan) {
        this.bankSlogan = bankSlogan;
    }

    public String getReceivingUser() {
        return receivingUser;
    }

    public void setReceivingUser(String receivingUser) {
        this.receivingUser = receivingUser;
    }
}
