package com.romepenaflor.myfancypdfinvoices.web.forms;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// backing bean in login.html
public class LoginForm {
    @NotBlank
    @Size(min = 5,  max = 20)
    private String username, password;
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
