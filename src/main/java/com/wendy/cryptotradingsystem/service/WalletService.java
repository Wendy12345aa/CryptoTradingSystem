package com.wendy.cryptotradingsystem.service;

import com.wendy.cryptotradingsystem.model.CryptoWalletBalance;
import com.wendy.cryptotradingsystem.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletService {

    @Autowired
    private CryptoWalletBalanceRepository cryptoWalletBalanceRepository; // This would be a repository for managing user data

    public List<CryptoWalletBalance> getUserWalletBalance(Customer customer){
        return cryptoWalletBalanceRepository.findAllByCustomer(customer);
    }

}
