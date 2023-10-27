package com.wendy.cryptotradingsystem.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
@Entity
public class CryptoWalletBalance {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long balanceId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Customer customer;

    @Column(length = 10, nullable = false)
    private String currencySymbol;

    @Column(precision = 18, scale = 8, nullable = false)
    private BigDecimal balance;

    public CryptoWalletBalance(Customer customer) {
        this.customer = customer;
    }

    public CryptoWalletBalance() {

    }

    @Override
    public String toString() {
        return "CryptoWalletBalance{" +
                "balanceId=" + balanceId +
                ", customer=" + customer +
                ", currencySymbol='" + currencySymbol + '\'' +
                ", balance=" + balance +
                '}';
    }

    public Long getBalanceId() {
        return balanceId;
    }

    public void setBalanceId(Long balanceId) {
        this.balanceId = balanceId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
