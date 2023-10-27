package com.wendy.cryptotradingsystem.controller;

import com.wendy.cryptotradingsystem.model.CryptoWalletBalance;
import com.wendy.cryptotradingsystem.model.Customer;
import com.wendy.cryptotradingsystem.service.TradeService;
import com.wendy.cryptotradingsystem.service.UserService;
import com.wendy.cryptotradingsystem.service.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/wallet")
public class CryptoCurrenciesWalletController {

    static Logger logger = LoggerFactory.getLogger(TradeService.class);

    @Autowired
    private WalletService walletService;

    @Autowired
    private UserService userService;

    /**
     * Retrieve the crypto wallet balances for a specific user.
     *
     * @param userName The username of the user to retrieve wallet balances for.
     * @return A ResponseEntity containing the user's crypto wallet balances.
     */
    @GetMapping("/balance")
    public ResponseEntity<List<CryptoWalletBalance>> getUserWalletBalance(@RequestParam String userName) {
        Customer user =  userService.getUser(userName);
        List<CryptoWalletBalance> balance = new ArrayList<>();
        if(user==null){
            balance.add(new CryptoWalletBalance( new Customer("Invalid User.")));
            return ResponseEntity.badRequest().body(balance);
        }

        balance = walletService.getUserWalletBalance(user);
        logger.debug(balance.toString());

        if(balance.size()<1){
            balance.add(new CryptoWalletBalance( new Customer("User have no balance.")));
            return ResponseEntity.ok(balance);
        }

        return ResponseEntity.ok(balance);
    }
}
