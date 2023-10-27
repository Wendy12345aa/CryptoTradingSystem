package com.wendy.cryptotradingsystem.model;


import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(length = 50, nullable = false)
    private String username;
    @Column(precision = 18, scale = 8, columnDefinition = "DECIMAL(18, 8) DEFAULT 50000.00000000")
    private BigDecimal walletBalance;

    public Customer(String username) {
        this.username = username;
    }

    public Customer() {
    }

    @Override
    public String toString() {
        return "Customer{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", walletBalance=" + walletBalance +
                '}';
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BigDecimal getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(BigDecimal walletBalance) {
        this.walletBalance = walletBalance;
    }
}