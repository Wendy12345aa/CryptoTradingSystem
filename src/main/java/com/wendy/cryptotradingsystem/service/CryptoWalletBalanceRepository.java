package com.wendy.cryptotradingsystem.service;

import com.wendy.cryptotradingsystem.model.AggregatedPrice;
import com.wendy.cryptotradingsystem.model.CryptoWalletBalance;
import com.wendy.cryptotradingsystem.model.Customer;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CryptoWalletBalanceRepository extends JpaRepository<CryptoWalletBalance, Long> {
    // You can define custom query methods here if needed
    List<CryptoWalletBalance> findAllByCustomerAndCurrencySymbol(Customer customer, String symbol);

    List<CryptoWalletBalance> findAllByCustomer(Customer customer);
    CryptoWalletBalance findFirstByCustomerAndCurrencySymbol(Customer customer, String symbol);
}
