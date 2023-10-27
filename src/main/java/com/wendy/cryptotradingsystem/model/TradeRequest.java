package com.wendy.cryptotradingsystem.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class TradeRequest {
    private String customerName;
    private String symbol;
    private String action;
    private BigDecimal quantity;

    public TradeRequest(String customerName, String symbol, String action, BigDecimal price, BigDecimal quantity) {
        this.customerName = customerName;
        this.symbol = symbol;
        this.action = action;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "TradeRequest{" +
                "customerName='" + customerName + '\'' +
                ", symbol='" + symbol + '\'' +
                ", action='" + action + '\'' +
                ", quantity=" + quantity +
                '}';
    }

    public TradeRequest(){}

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }


}
