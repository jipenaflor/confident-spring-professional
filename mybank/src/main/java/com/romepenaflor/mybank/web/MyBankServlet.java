package com.romepenaflor.mybank.web;

import com.romepenaflor.mybank.context.Application;
import com.romepenaflor.mybank.model.Transaction;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class MyBankServlet extends HttpServlet {

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
            List<Transaction> transactions = Application.transactionService.findAll();
            response.setContentType("application/json; charset=UTF-8");
            String json = Application.objectMapper.writeValueAsString(transactions);
            response.getWriter().print(json);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getRequestURI().equalsIgnoreCase("/transactions")) {
            BigDecimal amount = BigDecimal.valueOf(Double.valueOf(request.getParameter("amount")));
            String reference = request.getParameter("reference");

            Transaction transaction = Application.transactionService.create(amount, reference);

            response.setContentType("application/json; charset=UTF-8");
            String json = Application.objectMapper.writeValueAsString(transaction);
            response.getWriter().print(json);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
