package com.wendy.cryptotradingsystem.service;

import com.wendy.cryptotradingsystem.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private CustomerRepository userRepository; // This would be a repository for managing user data

    public Customer getUser(String userName) {
        return userRepository.findFirstByUsername(userName);
    }
}
