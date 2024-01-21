package com.tradems.app.tradestorems.service;

import com.tradems.app.tradestorems.model.Trade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScheduledTaskService {

    @Autowired
    private TradeService tradeService;

    @Scheduled(fixedRate = 5000)
    public void execute() {
        System.out.println("Periodically checking if there are any trades with previous maturity date ....");

        List<Trade> expiredTrades = tradeService.getExpiredTrades();

        if(expiredTrades.isEmpty()) {
            System.out.println("No trades found with previous expiry dates, no need to update expired flag");
        } else {
            System.out.println("Number of  trades with previous expiry dates = " + expiredTrades.size());
            System.out.println("Updating expired flag ");
            tradeService.updateExpiredTrades();
        }

    }
}
