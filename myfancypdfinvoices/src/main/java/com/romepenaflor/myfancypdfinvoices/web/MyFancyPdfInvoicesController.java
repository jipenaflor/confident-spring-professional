package com.romepenaflor.myfancypdfinvoices.web;

import com.romepenaflor.myfancypdfinvoices.model.Invoice;
import com.romepenaflor.myfancypdfinvoices.service.InvoiceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
@RestController = @Controller + @ResponseBody
@Controller - signals Spring that class' methods can return HTTP objects to end-user
@ResponseBody - tells Spring that you want to write JSON/ XML to HttpServletOutputstream
    without going through an HTML templating framework (default if without the annotation)
*/
@RestController
public class MyFancyPdfInvoicesController {

    private final InvoiceService invoiceService;

    public MyFancyPdfInvoicesController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/invoices")
    // @RequestMapping(value = "/invoices", method = RequestMethod.GET)
    public List<Invoice> invoices() {
        return invoiceService.findAll();
    }

    @PostMapping("/invoices")
    public Invoice createInvoice(@RequestParam("user_id") String userId,
                                 @RequestParam Integer amount) {
        return invoiceService.create(userId, amount);
    }
}
