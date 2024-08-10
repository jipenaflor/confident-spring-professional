package com.romepenaflor.myfancypdfinvoices.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/*
@Controller is an abstraction on top of HttpServlets
*/
@Controller
public class MyFirstSpringController {
    @GetMapping("/")
    @ResponseBody
    public String index() {
        return "Hello world!";
    }
}
