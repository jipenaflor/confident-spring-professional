package com.romepenaflor.mybank.service;

import com.romepenaflor.mybank.model.Transaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class TransactionService {
    private final JdbcTemplate jdbcTemplate;
    private final String bankSlogan;
    private static final ZoneId DEFAULT_ZONE = ZoneId.systemDefault();
    // List<Transaction> transactions = new CopyOnWriteArrayList<>();

    public TransactionService(JdbcTemplate jdbcTemplate, @Value("${bank.slogan}") String bankSlogan) {
        this.bankSlogan = bankSlogan;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public List<Transaction> findAll() {
        return jdbcTemplate.query("select * from transactions",
            TransactionService::rowMapper);
    }

    public List<Transaction> findByReceivingUserId(String userId) {
        /*
        return transactions.stream()
                .filter(transaction -> userId.equalsIgnoreCase(transaction.getReceivingUser()))
                .toList();
        */
        return jdbcTemplate.query(con -> {
            PreparedStatement ps = con.prepareStatement(
                    "select * from transactions where receiving_user = ?"
            );
            ps.setString(1, userId);
            return ps;
        }, TransactionService::rowMapper);
    }

    public Transaction create(BigDecimal amount, String reference, String receivingUser) {
        ZonedDateTime timestamp = LocalDateTime.now().atZone(DEFAULT_ZONE);
        Transaction transaction = new Transaction(amount, timestamp, reference, bankSlogan, receivingUser);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    "insert into transactions (amount, timestamp, reference, bank_slogan, receiving_user) values (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setBigDecimal(1, transaction.getAmount());
            ps.setTimestamp(2, Timestamp.valueOf(transaction.getTimestamp().toLocalDateTime()));
            ps.setString(3, transaction.getReference());
            ps.setString(4, transaction.getBankSlogan());
            ps.setString(5, transaction.getReceivingUser());
            return ps;
        }, keyHolder);

        String uuid = !keyHolder.getKeys().isEmpty() ? keyHolder.getKeys().values().iterator().next().toString() : null;
        transaction.setId(uuid);
        return transaction;
    }

    private static Transaction rowMapper(ResultSet rs, int rowNum) throws SQLException {
        Transaction transaction = new Transaction();
        transaction.setId(rs.getObject("id").toString());
        transaction.setAmount(rs.getBigDecimal("amount"));
        transaction.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime().atZone(DEFAULT_ZONE));
        transaction.setReference(rs.getString("reference"));
        transaction.setBankSlogan(rs.getString("bank_slogan"));
        transaction.setReceivingUser(rs.getString("receiving_user"));
        return transaction;
    }
}
