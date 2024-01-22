package com.tradems.app.tradestorems.controller;

import com.tradems.app.tradestorems.model.Trade;
import com.tradems.app.tradestorems.service.TradeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TradeStoreController {

    @Autowired
    private TradeService tradeService;

    static  Logger logger
            = LoggerFactory.getLogger(TradeStoreController.class);


    @RequestMapping(value="/trades", method= RequestMethod.POST)
    public Trade createTrade(@RequestBody Trade trade) {

        if(trade.getTradeId() == null || trade.getVersion() == 0 ||
            trade.getMaturityDate() == null || trade.getCreatedDate() == null) {


            logger.warn("Invalid trade record");
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid Trade request");

        }

        return tradeService.createTrade(trade);
    }


    @RequestMapping(value="/trades", method=RequestMethod.GET)
    public List<Trade> readTrades() {

        logger.info("Inside readTrades");
        return tradeService.getTrades();
    }



    @RequestMapping(value="/trades/{tradeId}", method=RequestMethod.GET)
    public List<Trade> readTradesbyId(@PathVariable(value = "tradeId") String id) {

        try {
            List<Trade> trades = tradeService.getTradesByTradeId(id);
            return trades;
        } catch (NoSuchElementException exception) {
            logger.warn("Trade Not Found");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Trade Not Found", exception);

        }


    }

    @RequestMapping(value="/trades/{tradeId}/{version}", method=RequestMethod.GET)
    public Trade readTradesbyIdAndVersion(@PathVariable(value = "tradeId") String id,
                                                @PathVariable(value = "version") int version) {

        try {
            Trade trade = tradeService.getTradesByIdVersion(id,version);

            if(trade == null) {
                logger.warn("Trade Not Found");
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Trade Not Found");
            }

            return trade;
        } catch (NoSuchElementException exception) {

            logger.warn("Trade Not Found");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Trade Not Found", exception);

        }


    }

    @RequestMapping(value="/trades/expired", method=RequestMethod.GET)
    public List<Trade> getExpiredTrades(@RequestParam(name = "expired") String expiredFlag) {

        logger.info("Inside getExpiredTrades");

        return tradeService.getExpiredTrades();
    }

    @RequestMapping(value="/trades/expired", method=RequestMethod.PUT)
    public List<Trade> updateExpiredTrades(@RequestParam(name = "expired") String expiredFlag) {

        logger.info("Inside updateExpiredTrades");

        return tradeService.updateExpiredTrades();
    }


    @RequestMapping(value="/trades/{tradeId}/{version}", method=RequestMethod.PUT)
    public Trade updateTrade(@PathVariable(value = "tradeId") String id,
                             @PathVariable(value = "version") int version,
                             @RequestBody Trade tradeDetails) {

        logger.info("Inside updateTrade");

        return tradeService.updateTrade(id, version, tradeDetails);
    }


    @RequestMapping(value="/trades/{tradeId}/{version}", method=RequestMethod.DELETE)
    public void deleteTrades(@PathVariable(value = "tradeId") String id,
                             @PathVariable(value = "version") int version) {
        logger.info("Inside deleteTrades");
        tradeService.deleteTrade(id, version);
    }



}
