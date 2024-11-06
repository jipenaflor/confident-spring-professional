package com.jeromepenaflor.myfancypdfinvoices.springboot.service;

import com.jeromepenaflor.myfancypdfinvoices.springboot.model.Invoice;

public interface InvoiceService {
    public Iterable<Invoice> findAll();
    public Invoice create(String userId, Integer amount);
}
