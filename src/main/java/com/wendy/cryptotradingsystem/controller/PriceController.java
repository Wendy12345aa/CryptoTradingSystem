package com.wendy.cryptotradingsystem.controller;

import com.wendy.cryptotradingsystem.model.AggregatedPrice;
import com.wendy.cryptotradingsystem.service.PriceAggregationService;
import com.wendy.cryptotradingsystem.service.TradeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;


@RestController
@RequestMapping("/api/prices")
public class PriceController {
    @Autowired
    private PriceAggregationService priceAggregationService;

    /**
     * Retrieve the latest aggregated price for a specific trading pair symbol.
     *
     * @param symbol The trading pair symbol to retrieve the price for (e.g., ETHUSDT, BTCUSDT).
     * @return A ResponseEntity containing the latest aggregated price response.
     */
    @GetMapping("/latest")
    public ResponseEntity<AggregatedPrice> getLatestAggregatedPrice(
            @RequestParam String symbol) {

        if ("ETHUSDT".equals(symbol) || "BTCUSDT".equals(symbol)) {
            AggregatedPrice latestAggregatedPrice = null;

                latestAggregatedPrice = priceAggregationService.getLatestBestBidPrice(symbol);

            return ResponseEntity.ok(latestAggregatedPrice);
        } else {
            AggregatedPrice errorResponse = new AggregatedPrice("Invalid trading pair. Supported pairs: ETHUSDT, BTCUSDT","", BigDecimal.ZERO, BigDecimal.ZERO);
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
}
