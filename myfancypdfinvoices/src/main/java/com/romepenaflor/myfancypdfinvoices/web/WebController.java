package com.romepenaflor.myfancypdfinvoices.web;

import com.romepenaflor.myfancypdfinvoices.web.forms.LoginForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login.html";
    }
    
    // @ModelAttribute makes sure that you can access the form in the model as if model.put(...)
    // binding result is put right after the loginForm to bind with it
    @PostMapping("/login")
    public String login(@ModelAttribute @Valid LoginForm loginForm,BindingResult bindingResult,  Model model ){
        /*
        added to catch the exception thrown by GlobalExceptionHandler so that appropriate error
        message is displayed at the frontend
        */
        if (bindingResult.hasErrors()) {
            return "login.html";
        }
        if (loginForm.getUsername().equals(loginForm.getPassword())) {
            return "redirect:/";
        }
        // reveal invalid credentials notification in login.html
        model.addAttribute("invalidCredentials", "true");
        return "login.html";
    }
}
