package com.wendy.cryptotradingsystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class CryptoTradingSystemApplication {
	static Logger logger = LoggerFactory.getLogger(CryptoTradingSystemApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CryptoTradingSystemApplication.class, args);
		logger.info("== Start Cryto Trading System ==");
	}

}
