package com.romepenaflor.myfancypdfinvoices.service;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

// @Profile - specify different properties or Spring beans in different environment
// run with -Dspring.profiles.active=dev
@Service
@Profile("dev")
public class DummyInvoiceService {

    private final InvoiceService invoiceService;

    public DummyInvoiceService(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostConstruct
    public void setup() {
        System.out.println("Creating dev invoices...");
        invoiceService.create("someUserId", 50);
        invoiceService.create("someOtherUserId", 100);
    }

}
