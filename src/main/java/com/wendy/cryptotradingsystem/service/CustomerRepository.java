package com.wendy.cryptotradingsystem.service;

import com.wendy.cryptotradingsystem.model.CryptoWalletBalance;
import com.wendy.cryptotradingsystem.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // You can define custom query methods here if needed
    Customer findFirstByUsername(String name);
}