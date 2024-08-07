package com.romepenaflor.myfancypdfinvoices.service;

import com.romepenaflor.myfancypdfinvoices.model.User;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserService {

    /*
    @Component turns services into @Beans
     */

    public User findById(String id) {
        // dummy, since there is no database
        String randomName = UUID.randomUUID().toString();
        return new User(id, randomName);
    }
}
