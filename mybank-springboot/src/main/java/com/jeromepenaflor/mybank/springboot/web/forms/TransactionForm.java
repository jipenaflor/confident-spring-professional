package com.jeromepenaflor.mybank.springboot.web.forms;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class TransactionForm {
    @NotNull
    @DecimalMin("0.01")
    @Max(1000)
    private BigDecimal amount;
    @NotBlank
    @Size(min = 1, max = 25)
    private String reference;
    @NotBlank
    private String receivingUser;

    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getReference() {
        return reference;
    }
    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getReceivingUser() {
        return receivingUser;
    }
    public void setReceivingUser(String receivingUser) {
        this.receivingUser = receivingUser;
    }
}
