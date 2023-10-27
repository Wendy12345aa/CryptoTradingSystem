package com.wendy.cryptotradingsystem.service;

import com.wendy.cryptotradingsystem.model.Customer;
import com.wendy.cryptotradingsystem.model.TradingTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradingHistoryService {

    @Autowired
    private TradingTransactionRepository tradeRepository; // This would be a repository for managing trade data

    @Autowired
    private UserService userService;

    public List<TradingTransaction> getUserTradingHistory(String userName) {
        Customer user =  userService.getUser(userName);
        return tradeRepository.getTradingTransactionByCustomer(user);

    }
}
