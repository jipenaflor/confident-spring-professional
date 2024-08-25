package com.romepenaflor.mybank.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.romepenaflor.mybank.context.Application;
import com.romepenaflor.mybank.model.Transaction;
import com.romepenaflor.mybank.service.TransactionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class MyBankServlet extends HttpServlet {
    /*
    private TransactionService transactionService;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext(Application.class);

        this.transactionService = ctx.getBean(TransactionService.class);
        this.objectMapper = ctx.getBean(ObjectMapper.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getRequestURI().equalsIgnoreCase("/")) {
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().print("<html>\n" +
                    "<body>\n" +
                    "<h1>Hello World</h1>\n" +
                    "<p>This is my Bank</p>\n" +
                    "</body>\n" +
                    "</html>");
        } else if (request.getRequestURI().equalsIgnoreCase("/transactions")) {
            List<Transaction> transactions = transactionService.findAll();
            response.setContentType("application/json; charset=UTF-8");
            String json = objectMapper.writeValueAsString(transactions);
            response.getWriter().print(json);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getRequestURI().equalsIgnoreCase("/transactions")) {
            BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(request.getParameter("amount")));
            String reference = request.getParameter("reference");

            Transaction transaction = transactionService.create(amount, reference);

            response.setContentType("application/json; charset=UTF-8");
            String json = objectMapper.writeValueAsString(transaction);
            response.getWriter().print(json);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    */
}

