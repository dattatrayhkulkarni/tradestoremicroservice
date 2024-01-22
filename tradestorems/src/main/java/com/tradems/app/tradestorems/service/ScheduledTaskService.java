package com.tradems.app.tradestorems.service;

import com.tradems.app.tradestorems.controller.TradeStoreController;
import com.tradems.app.tradestorems.model.Trade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScheduledTaskService {

    @Autowired
    private TradeService tradeService;

    static Logger logger
            = LoggerFactory.getLogger(TradeStoreController.class);


    @Scheduled(fixedRate = 10000)
    public void execute() {

        logger.info("Periodically checking if there are any trades with previous maturity date ....");

        List<Trade> expiredTrades = tradeService.getExpiredTrades();

        if(expiredTrades.isEmpty()) {
            logger.info("No trades found with previous expiry dates, no need to update expired flag");
        } else {
            logger.info("Number of  trades with previous expiry dates = " + expiredTrades.size());
            logger.info("Updating expired flag");
            tradeService.updateExpiredTrades();
        }

    }
}
