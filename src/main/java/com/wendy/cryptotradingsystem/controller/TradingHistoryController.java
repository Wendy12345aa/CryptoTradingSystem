package com.wendy.cryptotradingsystem.controller;

import com.wendy.cryptotradingsystem.model.TradingTransaction;
import com.wendy.cryptotradingsystem.service.TradingHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/history")
public class TradingHistoryController {

    @Autowired
    private TradingHistoryService tradingHistoryService;

    /**
     * Retrieve the trading history for a specific user.
     *
     * @param userName The username of the user for whom the trading history is requested.
     * @return A ResponseEntity containing the list of trading transactions for the user.
     */
    @GetMapping("/user/{userName}")
    public ResponseEntity<List<TradingTransaction>> getUserTradingHistory(@PathVariable String userName) {
        List<TradingTransaction> history = tradingHistoryService.getUserTradingHistory(userName);
        return ResponseEntity.ok(history);
    }
}