package com.jeromepenaflor.myfancypdfinvoices.springboot.service;

import com.jeromepenaflor.myfancypdfinvoices.springboot.model.Invoice;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;

@Component
public class InvoiceServiceImpl implements InvoiceService {

    private final UserService userService;
    private final String cdnUrl;
    private final JdbcTemplate jdbcTemplate;

    public InvoiceServiceImpl(UserService userService,
                              @Value("${cdn.url}") String cdnUrl, JdbcTemplate jdbcTemplate) {
        this.userService = userService;
        this.cdnUrl = cdnUrl;
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void init() {
        System.out.println("Fetching PDF Template from S3...");
        // TODO download from s3 and save locally
    }

    @PreDestroy
    public void shutdown() {
        System.out.println("Deleting downloaded templates...");
        // TODO actual deletion of PDFs
    }

    public Iterable<Invoice> findAll() {
        return jdbcTemplate.query("select * from \"invoices\"", (resultSet, rowNum) -> {
            Invoice invoice = new Invoice();
            invoice.setId(resultSet.getObject("id").toString());    // Not a simple string, but a UUID string
            invoice.setPdfUrl(resultSet.getString("pdf_url"));
            invoice.setUserId(resultSet.getString("user_id"));
            invoice.setAmount(resultSet.getInt("amount"));
            return invoice;
        });
    }

    public Iterable<Invoice> findByUserId(String userId) {
        return jdbcTemplate.query("select * from \"invoices\" where user_id = ?", (resultSet, rowNum) -> {
            Invoice invoice = new Invoice();
            invoice.setId(resultSet.getObject("id").toString());    // Not a simple string, but a UUID string
            invoice.setPdfUrl(resultSet.getString("pdf_url"));
            invoice.setUserId(resultSet.getString("user_id"));
            invoice.setAmount(resultSet.getInt("amount"));
            return invoice;
        }, userId);
    }

    public Invoice create(String userId, Integer amount) {
        String generatedPdfUrl = cdnUrl + "/images/default/sample.pdf";

        // make sure that the generated primary key is available via the keyholder
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement("insert into \"invoices\" (user_id, pdf_url, amount) values (?, ?, ?)",
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
}
