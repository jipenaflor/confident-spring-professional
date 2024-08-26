package com.romepenaflor.myfancypdfinvoices.service;

import com.romepenaflor.myfancypdfinvoices.model.Invoice;
import com.romepenaflor.myfancypdfinvoices.model.User;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

// @Component with stronger semantic meaning = @Services
@Service
public class InvoiceService {
    private final JdbcTemplate jdbcTemplate;

    // Field injection: constructor and final modifier removed, replaced with @Autowired
    // @Autowired
    private final UserService userService;
    private final String cdnUrl;

    List<Invoice> invoices = new CopyOnWriteArrayList<>();

    // Spring injects fields after calling the constructor
    // Inject properties with @Value
    public InvoiceService(UserService userService, JdbcTemplate jdbcTemplate,
                          @Value("${cdn.url}") String cdnUrl) {
        this.userService = userService;
        this.cdnUrl = cdnUrl;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public List<Invoice> findAll() {
        System.out.println("Is a database transaction open? = " + TransactionSynchronizationManager.isActualTransactionActive());
        // row mapper lets you map every returned row to a Java object
        return jdbcTemplate.query("select id, user_id, pdf_url, amount from invoices", (resultSet, rowNum) -> {
            Invoice invoice = new Invoice();
            invoice.setId(resultSet.getObject("id").toString());    // Not a simple string, but a UUID string
            invoice.setPdfUrl(resultSet.getString("pdf_url"));
            invoice.setUserId(resultSet.getString("user_id"));
            invoice.setAmount(resultSet.getInt("amount"));
            return invoice;
        });
    }

    @Transactional
    public Invoice create(String userId, Integer amount) {
        System.out.println("Is a database transaction open? = " + TransactionSynchronizationManager.isActualTransactionActive());
        String generatedPdfUrl = cdnUrl + "/images/default/sample.pdf";

        // make sure that the generated primary key is available via the keyholder
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement("insert into invoices (user_id, pdf_url, amount) values (?, ?, ?)",
                            Statement.RETURN_GENERATED_KEYS);
            // setting parameters replaced the (?, ?, ?); safeguard against SQL-injections
            ps.setString(1, userId);
            ps.setString(2, generatedPdfUrl);
            ps.setInt(3, amount);
            return ps;
        }, keyHolder);

        /* return the auto-generated UUID primary key from the database
        GeneratedKeyHolder has a convenient way to return a numeric one, but not for the UUIDs, hence,
        the use of ternary operators
        */
        String uuid = !keyHolder.getKeys().isEmpty() ? ((UUID) keyHolder.getKeys().
                values().iterator().next()).toString() : null;

        // create invoice object
        Invoice invoice = new Invoice();
        invoice.setId(uuid);
        invoice.setPdfUrl(generatedPdfUrl);
        invoice.setAmount(amount);
        invoice.setUserId(userId);
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
