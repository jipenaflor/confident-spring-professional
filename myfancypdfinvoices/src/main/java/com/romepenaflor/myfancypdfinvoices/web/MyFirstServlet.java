package com.romepenaflor.myfancypdfinvoices.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.romepenaflor.myfancypdfinvoices.context.MyFancyPdfInvoicesApplicationConfiguration;
import com.romepenaflor.myfancypdfinvoices.model.Invoice;
import com.romepenaflor.myfancypdfinvoices.service.InvoiceService;
import com.romepenaflor.myfancypdfinvoices.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.List;

public class MyFirstServlet extends HttpServlet {
    private UserService userService;
    private ObjectMapper objectMapper;
    private InvoiceService invoiceService;

    @Override
    public void init() throws ServletException {
        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext(MyFancyPdfInvoicesApplicationConfiguration.class);

        /*
        Whenever the JVM is about to stop, Spring calls @PreDestroy methods of registered beans
        to shut down the applicationContext first
        */
        ctx.registerShutdownHook();
        
        this.userService = ctx.getBean(UserService.class);
        this.objectMapper = ctx.getBean(ObjectMapper.class);
        this.invoiceService = ctx.getBean(InvoiceService.class);
        
        /*
        ctx reads @Configuration class and construct your @beans services,
        now we save the beans from ApplicationContext as fields in the servlet
        */

        /* try if @Scope creates new instance of UserService every time
        System.out.println(ctx.getBean(UserService.class));
        System.out.println(ctx.getBean(UserService.class));
        System.out.println(ctx.getBean(InvoiceService.class));
        System.out.println(ctx.getBean(InvoiceService.class));
         */
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // let the browser know you are sending text/html
        // write to response which is sent to the browser by servlet container
        if (request.getRequestURI().equalsIgnoreCase("/")) {
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().print("<html>\n" +
                    "<body>\n" +
                    "<h1>Hello World</h1>\n" +
                    "<p>This is my very first, embedded Tomcat, HTML Page!</p>\n" +
                    "</body>\n" +
                    "</html>");
        } else if (request.getRequestURI().equalsIgnoreCase("/invoices")){
            List<Invoice> invoices = invoiceService.findAll();
            response.setContentType("application/json; charset=UTF-8");
            String json = objectMapper.writeValueAsString(invoices);
            response.getWriter().print(json);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getRequestURI().equalsIgnoreCase("/invoices")) {
            String userId = request.getParameter("user_id");
            Integer amount = Integer.valueOf(request.getParameter("amount"));

            Invoice invoice = invoiceService.create(userId, amount);

            response.setContentType("application/json; charset=UTF-8");
            // convert invoice object to JSON string
            String json = objectMapper.writeValueAsString(invoice);
            response.getWriter().print(json);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
