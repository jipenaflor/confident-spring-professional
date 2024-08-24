package com.romepenaflor.myfancypdfinvoices.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class WebController {
    @GetMapping("/")
    public String homepage(Model model,
                           @RequestParam(required = false, defaultValue = "stranger") String username) {
        model.addAttribute("username", username);
        model.addAttribute("currentDate", LocalDateTime.now());
        return "index.html";
    }
}
