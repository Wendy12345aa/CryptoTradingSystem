package com.wendy.cryptotradingsystem.service;

import com.wendy.cryptotradingsystem.model.Customer;
import com.wendy.cryptotradingsystem.model.TradingTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradingTransactionRepository extends JpaRepository<TradingTransaction, Long> {
    // You can define custom query methods here if needed
    public List<TradingTransaction> getTradingTransactionByCustomer(Customer customer);
}