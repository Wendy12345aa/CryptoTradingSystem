package com.wendy.cryptotradingsystem.controller;

import com.wendy.cryptotradingsystem.model.TradeRequest;
import com.wendy.cryptotradingsystem.model.TradingTransaction;
import com.wendy.cryptotradingsystem.service.TradeException;
import com.wendy.cryptotradingsystem.service.TradeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trade")
public class TradeController {

    @Autowired
    private TradeService tradeService;

    /**
     * Execute a trade based on the provided trade request.
     *
     * @param tradeRequest The trade request containing details of the trade.
     * @return A ResponseEntity with a success message if the trade is executed successfully, or a bad request message if there is an issue.
     * @throws HttpMessageNotReadableException if the request body is missing or cannot be read.
     */
    @PostMapping("/execute")
    public ResponseEntity<String> executeTrade( @RequestBody TradeRequest tradeRequest) throws HttpMessageNotReadableException {

        try {
            // Validate and process the trade request
            tradeService.executeTrade(tradeRequest);
            return ResponseEntity.ok("Trade executed successfully");
        } catch (TradeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body("Invalid request body. Please provide a valid JSON request body.");
    }


}
