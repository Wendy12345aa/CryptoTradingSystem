package com.wendy.cryptotradingsystem.service;


import com.wendy.cryptotradingsystem.model.AggregatedPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AggregatedPriceRepository extends JpaRepository<AggregatedPrice, Long> {
    // You can define custom query methods here if needed
    AggregatedPrice findFirstBySymbolOrderByTimestampDesc(String symbol);
}