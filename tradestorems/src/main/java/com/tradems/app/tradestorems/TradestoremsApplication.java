package com.tradems.app.tradestorems;

import com.tradems.app.tradestorems.controller.TradeStoreController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TradestoremsApplication {

	static  Logger logger
			= LoggerFactory.getLogger(TradeStoreController.class);

	public static void main(String[] args) {

		logger.info("Before Starting application");

		SpringApplication.run(TradestoremsApplication.class, args);
	}

}
