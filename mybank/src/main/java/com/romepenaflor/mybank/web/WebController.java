package com.romepenaflor.mybank.web;

import com.romepenaflor.mybank.model.Transaction;
import com.romepenaflor.mybank.service.TransactionService;
import com.romepenaflor.mybank.web.forms.TransactionForm;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class WebController {
    private final TransactionService transactionService;
    private final String bankSlogan;

    public WebController(TransactionService transactionService,
                         @Value("${bank.slogan}") String bankSlogan) {
        this.transactionService = transactionService;
        this.bankSlogan = bankSlogan;
    }

    @GetMapping("/")
    public String homepage(Model model) {
        model.addAttribute("bankSlogan", bankSlogan);
        return "index.html";
    }

    @GetMapping("/account/{userId}")
    public String userTransaction(Model model, @PathVariable String userId) {
        model.addAttribute("userId", userId);
        model.addAttribute("transactions", transactionService.findByReceivingUserId(userId));
        model.addAttribute("txForm", new TransactionForm());
        return "transactions.html";
    }

    @PostMapping("/account/{userId}")
    public String newTransaction(@ModelAttribute("txForm") @Valid TransactionForm txForm,
                                 BindingResult bindingResult,
                                 @PathVariable("userId") String userId,
                                 Model model) {

        model.addAttribute("userId", userId);
        model.addAttribute("transactions", transactionService.findByReceivingUserId(userId));

        if (bindingResult.hasErrors()) {
            model.addAttribute("txError", true);
            return "transactions.html";
        }

        transactionService.create(txForm.getAmount(), txForm.getReference(), txForm.getReceivingUser());
        return "redirect:/account/" + userId;
    }
}
