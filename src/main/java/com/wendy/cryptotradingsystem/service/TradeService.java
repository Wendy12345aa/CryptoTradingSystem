package com.wendy.cryptotradingsystem.service;

import com.wendy.cryptotradingsystem.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

import static com.wendy.cryptotradingsystem.service.PriceAggregationService.SUPPORTED_TRADING_PAIRS;

@Service
public class TradeService {
    static Logger logger = LoggerFactory.getLogger(TradeService.class);

    @Autowired
    private PriceAggregationService priceAggregationService;

    @Autowired
    private TradingTransactionRepository tradingHistoryRepository;

    @Autowired
    private CryptoWalletBalanceRepository cryptoWalletBalanceRepository; // This would be a repository for managing crypto Wallet Balance

    @Autowired
    private CustomerRepository userRepository; // This would be a repository for managing user data

    @Autowired
    private TradingTransactionRepository tradeRepository; // This would be a repository for managing trade data

    public void executeTrade(TradeRequest tradeRequest) throws TradeException {
        // Validate the trade request
        if (tradeRequest == null
                || (tradeRequest.getAction() == null && tradeRequest.getQuantity() == null
                && tradeRequest.getSymbol()==null && tradeRequest.getCustomerName()== null) ) {
            throw new TradeException("Invalid trade request: Request is null");
        }
        logger.debug(tradeRequest.toString());
        // Check if the trading pair is supported (ETHUSDT or BTCUSDT)
        String symbol = tradeRequest.getSymbol();
        if (!SUPPORTED_TRADING_PAIRS.contains(symbol)) {
            throw new TradeException("Invalid trading pair: " + symbol);
        }

        // Retrieve the user's balance from the database
        Customer user = userRepository.findFirstByUsername(tradeRequest.getCustomerName());
        if (user == null) {
            throw new TradeException("User not found");
        }
        String CurrencySymbol = tradeRequest.getSymbol();
        BigDecimal price;
        BigDecimal newBalance;
        BigDecimal cost;
        AggregatedPrice latestBestBidPrice = priceAggregationService.getLatestBestBidPrice(CurrencySymbol);

        if (latestBestBidPrice == null){
            throw new TradeException("No latest Best Bid Price. Kindly retry");
        }
        // Calculate the trade cost or profit based on the action (BUY or SELL)

        BigDecimal quantity = tradeRequest.getQuantity();
        TransactionAction action = TransactionAction.valueOf(tradeRequest.getAction());

        if (TransactionAction.BUY.equals(action)) {
            price=latestBestBidPrice.getAskPrice();
            cost = price.multiply(quantity);
            // For a BUY order, check if the user has sufficient balance
            if (user.getWalletBalance().compareTo(cost) < 0) {
                throw new TradeException("Insufficient balance for the BUY order");
            }

            // Deduct the cost from the user's balance
            user.setWalletBalance(user.getWalletBalance().subtract(cost));

            if (!hasCryptocurrencyBalanceForSymbol(CurrencySymbol, user)) {
                CryptoWalletBalance newWalletBalance = new CryptoWalletBalance();
                newWalletBalance.setBalance(quantity);
                newWalletBalance.setCustomer(user);
                newWalletBalance.setCurrencySymbol(CurrencySymbol);
                cryptoWalletBalanceRepository.saveAndFlush(newWalletBalance);
            } else {
                CryptoWalletBalance cryptoWalletBalance = cryptoWalletBalanceRepository.findFirstByCustomerAndCurrencySymbol(user, symbol);
                newBalance = cryptoWalletBalance.getBalance().add(quantity);
                cryptoWalletBalance.setBalance(newBalance);
                cryptoWalletBalanceRepository.save(cryptoWalletBalance);

            }


        } else if (TransactionAction.SELL.equals(action)) {
            price=latestBestBidPrice.getBidPrice();
            cost = price.multiply(quantity);
            // Check if the trading pair in the trade request matches the cryptocurrency held by the user
            if (!hasCryptocurrencyBalanceForSymbol(tradeRequest.getSymbol(), user)) {
                throw new TradeException("User does not have the cryptocurrency " + symbol + " for the SELL order");
            }

            // Check if the user has a sufficient quantity of the cryptocurrency to sell
            BigDecimal quantityToSell = tradeRequest.getQuantity();
            CryptoWalletBalance cryptoWalletBalance = cryptoWalletBalanceRepository.findFirstByCustomerAndCurrencySymbol(user, symbol);
            BigDecimal userCryptocurrencyBalance = cryptoWalletBalance.getBalance();

            if (userCryptocurrencyBalance.compareTo(quantityToSell) < 0) {
                throw new TradeException("Insufficient cryptocurrency balance for the SELL order");
            }

            // If the checks pass, proceed with the SELL order execution
            // Add the profit to the user's balance
            user.setWalletBalance(user.getWalletBalance().add(cost));
            newBalance = userCryptocurrencyBalance.subtract(quantity);
            if (newBalance.equals(BigDecimal.ZERO)) {
                cryptoWalletBalanceRepository.delete(cryptoWalletBalance);
            } else {
                cryptoWalletBalance.setBalance(newBalance);
                cryptoWalletBalanceRepository.save(cryptoWalletBalance);           }

        } else {
            throw new TradeException("Invalid trade action: " + tradeRequest.getAction());
        }

        // Update the user's balance in the database
        userRepository.save(user);
        TradingTransaction newTransaction = new TradingTransaction();
        newTransaction.setCustomer(user);
        newTransaction.setAction(TransactionAction.valueOf(tradeRequest.getAction()));
        newTransaction.setPrice(price);
        newTransaction.setQuantity(tradeRequest.getQuantity());
        newTransaction.setSymbol(tradeRequest.getSymbol());
        logger.debug(newTransaction.toString());

        // Record the trade in the user's trading history
        tradingHistoryRepository.save(newTransaction);
    }

    public boolean hasCryptocurrencyBalanceForSymbol(String symbol, Customer customer) {

        // Check if the user has a balance for the specified symbol
        List<CryptoWalletBalance> cryptoWalletBalanceList = cryptoWalletBalanceRepository.findAllByCustomerAndCurrencySymbol(customer, symbol);
        return !cryptoWalletBalanceList.isEmpty();
    }


}