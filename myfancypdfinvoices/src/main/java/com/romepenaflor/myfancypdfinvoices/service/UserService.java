package com.romepenaflor.myfancypdfinvoices.service;

import com.romepenaflor.myfancypdfinvoices.model.User;

import java.util.UUID;

public class UserService {

    public User findById(String id) {
        // dummy, since there is no database
        String randomName = UUID.randomUUID().toString();
        return new User(id, randomName);
    }
}
