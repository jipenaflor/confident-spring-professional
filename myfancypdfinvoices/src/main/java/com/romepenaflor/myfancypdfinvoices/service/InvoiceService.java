package com.romepenaflor.myfancypdfinvoices.service;

import com.romepenaflor.myfancypdfinvoices.model.Invoice;
import com.romepenaflor.myfancypdfinvoices.model.User;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

// @Component with stronger semantic meaning = @Services
@Service
public class InvoiceService {

    // Field injection: constructor and final modifier removed, replaced with @Autowired
    // @Autowired
    private final UserService userService;
    private final String cdnUrl;

    List<Invoice> invoices = new CopyOnWriteArrayList<>();

    // Spring injects fields after calling the constructor
    // Inject properties with @Value
    public InvoiceService(UserService userService, @Value("${cdn.url}") String cdnUrl) {
        this.userService = userService;
        this.cdnUrl = cdnUrl;
    }

    public List<Invoice> findAll() {
        return invoices;
    }

    public Invoice create(String userId, Integer amount) {
        User user =  userService.findById(userId);
        if (user == null) {
            throw new IllegalStateException();
        }

        Invoice invoice = new Invoice(userId, amount, cdnUrl + "/images/default/sample.pdf");
        invoices.add(invoice);
        return invoice;
    }

    // @PostConstruct runs after the object, including its dependencies, is completely constructed
    @PostConstruct
    public void init() {
        System.out.println("Fetching PDF template from S3...");
    }

    /*
    @PreDestroy runs after ApplicationContext shuts down gracefully, for cleaning
    any open resources. Shutting down AppContext does not happen explicitly. Most IDEs
    do not shut down apps gracefully, they just terminate it.
     */
    @PreDestroy
    public void shutdown() {
        System.out.println("Deleting downloaded templates...");
    }

    /* Setter injection
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    */

}
