package com.wendy.cryptotradingsystem.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wendy.cryptotradingsystem.config.AppProperties;
import com.wendy.cryptotradingsystem.model.AggregatedPrice;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class PriceAggregationService {
    static Logger logger = LoggerFactory.getLogger(PriceAggregationService.class);
    ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate;
    public PriceAggregationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    private AggregatedPriceRepository priceRepository;

    @Autowired
    private AppProperties config;

    private final String SYMBOL_LABEL = "symbol";
    public static final Set<String> SUPPORTED_TRADING_PAIRS = Set.of("ETHUSDT", "BTCUSDT", "ethusdt", "btcusdt");
    public static final Set<String> SUPPORTED_TRADING_PAIRS_CHECKING = Set.of("ETHUSDT", "BTCUSDT");


    @Value("${scheduling.fixedRate}")
    private long fixedRate;

    @Value("${scheduling.initialDelay}")
    private long initialDelay;


    @PostConstruct
    public void init() {
        logger.info(config.printProperties());
    }

    public AggregatedPrice getLatestBestBidPrice(String symbol) {
        AggregatedPrice latestBestBidPrice = priceRepository.findFirstBySymbolOrderByTimestampDesc(symbol);
        return latestBestBidPrice;
    }


    @Scheduled(fixedRateString = "${scheduling.fixedRate}", initialDelayString = "${scheduling.initialDelay}")
    public void aggregateAndStorePrices() {


        // Send HTTP GET requests to Binance and Huobi
        String binanceResponse = restTemplate.getForObject(config.getUrl1(), String.class);
        String huobiResponse = restTemplate.getForObject(config.getUrl2(), String.class);

        List<AggregatedPrice> BinancePriceList = getFilterPriceListFromBinance(binanceResponse);
        List<AggregatedPrice> huobiPriceList = getFilterPriceListFromHoubi(huobiResponse);

        for (String tradingPair : SUPPORTED_TRADING_PAIRS_CHECKING) {
            BigDecimal bidPrice = calculateBestBidPrice(BinancePriceList,huobiPriceList,tradingPair).getBidPrice();
            BigDecimal askPrice = calculateBestAskPrice(BinancePriceList,huobiPriceList,tradingPair).getAskPrice();
            AggregatedPrice pricingData = new AggregatedPrice(tradingPair,"", bidPrice, askPrice);
            priceRepository.saveAndFlush(pricingData);
            logger.info("==  Added new price " + pricingData + " ==");



        }

    }

    private AggregatedPrice calculateBestBidPrice(List<AggregatedPrice> list1, List<AggregatedPrice> list2, String symbol) {
        BigDecimal maxPrice = BigDecimal.ZERO;

        AggregatedPrice maxPriceObj = new AggregatedPrice();

        for (AggregatedPrice data : list1) {
            if (data.getSymbol().equals(symbol)) {
                if ( data.getBidPrice().compareTo(maxPrice) > 0) {
                    maxPrice = data.getBidPrice();
                    maxPriceObj = data;
                }
            }
        }

        for (AggregatedPrice data : list2) {
            if (data.getSymbol().equals(symbol)) {
                if ( data.getBidPrice().compareTo(maxPrice) > 0) {
                    maxPrice = data.getBidPrice();
                    maxPriceObj = data;
                }
            }
        }

        return maxPriceObj;
    }

    private AggregatedPrice calculateBestAskPrice(List<AggregatedPrice> list1, List<AggregatedPrice> list2, String symbol) {
        BigDecimal minPrice = BigDecimal.valueOf(Double.MAX_VALUE);

        AggregatedPrice minPriceObj = new AggregatedPrice();

        for (AggregatedPrice data : list1) {
            if (data.getSymbol().equals(symbol)) {
                if (data.getAskPrice().compareTo(minPrice) < 0 ) {
                    minPrice = data.getAskPrice();
                    minPriceObj = data;
                }
            }
        }

        for (AggregatedPrice data : list2) {
            if (data.getSymbol().equals(symbol)) {
                if (data.getAskPrice().compareTo(minPrice) < 0 ) {
                    minPrice = data.getAskPrice();
                    minPriceObj = data;
                }
            }
        }

        return minPriceObj;
    }


    public boolean isSupportedTradingPair(String tradingPair) {
        return SUPPORTED_TRADING_PAIRS.contains(tradingPair);
    }

    public List<AggregatedPrice> getFilterPriceListFromBinance(String source) {

        List<AggregatedPrice> ListAggregatedPriceFromBinance = new ArrayList<>();
        BigDecimal bidPrice;
        BigDecimal askPrice;
        String symbol = "";

        try {
            // Parse the JSON response
            List<JsonNode> jsonNodes = objectMapper.readValue(source, objectMapper.getTypeFactory().constructCollectionType(List.class, JsonNode.class));
            for (JsonNode jsonNode : jsonNodes) {
                // Check if the "symbol" field equals SUPPORTED_TRADING_PAIRS
                if (jsonNode.has(SYMBOL_LABEL) && isSupportedTradingPair(jsonNode.get(SYMBOL_LABEL).asText())) {
                    bidPrice = BigDecimal.valueOf(jsonNode.get("bidPrice").asDouble());
                    askPrice = BigDecimal.valueOf(jsonNode.get("askPrice").asDouble());
                    symbol = jsonNode.get(SYMBOL_LABEL).asText();
                    AggregatedPrice pricingData = new AggregatedPrice(symbol, "Binance", bidPrice, askPrice);
                    ListAggregatedPriceFromBinance.add(pricingData);
                }
            }
        } catch (IOException e) {
            // Handle JSON parsing exceptions
            logger.error(e.getMessage());

        }

        return ListAggregatedPriceFromBinance;
    }




    public List<AggregatedPrice> getFilterPriceListFromHoubi(String source) {

        List<AggregatedPrice> ListAggregatedPriceFromBinance = new ArrayList<>();
        BigDecimal bidPrice;
        BigDecimal askPrice;
        String symbol = "";


        try {
            // Parse the JSON response
            JsonNode jsonNodes = objectMapper.readTree(source).get("data");
            for (JsonNode jsonNode : jsonNodes) {
                // Check if the "symbol" field equals SUPPORTED_TRADING_PAIRS
                if (jsonNode.has(SYMBOL_LABEL) && isSupportedTradingPair(jsonNode.get(SYMBOL_LABEL).asText())) {
                    bidPrice = BigDecimal.valueOf(jsonNode.get("bid").asDouble());
                    askPrice = BigDecimal.valueOf(jsonNode.get("ask").asDouble());
                    symbol = jsonNode.get(SYMBOL_LABEL).asText().toUpperCase();
                    AggregatedPrice pricingData = new AggregatedPrice(symbol, "Houbi",bidPrice, askPrice);
                    ListAggregatedPriceFromBinance.add(pricingData);
                }
            }
        } catch (IOException e) {
            // Handle JSON parsing exceptions
            logger.error(e.getMessage());

        }

        return ListAggregatedPriceFromBinance;
    }


}