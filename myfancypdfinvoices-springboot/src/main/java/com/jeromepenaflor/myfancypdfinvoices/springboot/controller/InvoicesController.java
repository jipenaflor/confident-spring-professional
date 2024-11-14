package com.jeromepenaflor.myfancypdfinvoices.springboot.controller;

import com.jeromepenaflor.myfancypdfinvoices.springboot.dto.InvoiceDto;
import com.jeromepenaflor.myfancypdfinvoices.springboot.model.Invoice;
import com.jeromepenaflor.myfancypdfinvoices.springboot.service.InvoiceService;
import com.jeromepenaflor.myfancypdfinvoices.springboot.service.InvoiceServiceImpl;
import com.jeromepenaflor.myfancypdfinvoices.springboot.service.InvoiceServiceJdbc;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InvoicesController {
    private final InvoiceServiceJdbc invoiceService;

    public InvoicesController(InvoiceServiceJdbc invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/invoices")
    // @RequestMapping(value = "/invoices", method = RequestMethod.GET)
    public Iterable<Invoice> invoices() {
        return invoiceService.findAll();
    }

    @GetMapping("/invoices/user/{userId}")
    public Iterable<Invoice> userInvoices(@PathVariable String userId) {
        return invoiceService.findByUserId(userId);
    }

    @PostMapping("/invoices")
    public Invoice createInvoice(@Valid @RequestBody InvoiceDto invoiceDto) {
        return invoiceService.create(invoiceDto.getUserId(), invoiceDto.getAmount());
    }
}
