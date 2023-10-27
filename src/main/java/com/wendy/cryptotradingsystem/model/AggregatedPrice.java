package com.wendy.cryptotradingsystem.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
public class AggregatedPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long priceId;

    @Column(length = 10, nullable = false)
    private String symbol;

    @Column(length = 10, nullable = false)
    private String source;

    @Column(precision = 18, scale = 8, nullable = false)
    private BigDecimal bidPrice;

    @Column(precision = 18, scale = 8, nullable = false)
    private BigDecimal askPrice;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp timestamp;

    public AggregatedPrice(){}

    public AggregatedPrice(String symbol, String source, BigDecimal bidPrice, BigDecimal askPrice) {
        this.symbol = symbol;
        this.source = source;
        this.bidPrice = bidPrice;
        this.askPrice = askPrice;
    }

    @Override
    public String toString() {
        return "AggregatedPrice{" +
                "priceId=" + priceId +
                ", symbol='" + symbol + '\'' +
                ", source='" + source + '\'' +
                ", bidPrice=" + bidPrice +
                ", askPrice=" + askPrice +
                ", timestamp=" + timestamp +
                '}';
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Long getPriceId() {
        return priceId;
    }

    public void setPriceId(Long priceId) {
        this.priceId = priceId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(BigDecimal bidPrice) {
        this.bidPrice = bidPrice;
    }

    public BigDecimal getAskPrice() {
        return askPrice;
    }

    public void setAskPrice(BigDecimal askPrice) {
        this.askPrice = askPrice;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
