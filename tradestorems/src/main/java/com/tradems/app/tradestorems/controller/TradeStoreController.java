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

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TradeStoreController {

    @Autowired
    private TradeService tradeService;


    @RequestMapping(value="/trades", method= RequestMethod.POST)
    public Trade createTrade(@RequestBody Trade trade) {
        System.out.println("------------------------");
        System.out.println(" **** Inside createTrade *****");
        System.out.println(trade);


        if(trade.getTradeId() == null || trade.getVersion() == 0 ||
            trade.getMaturityDate() == null || trade.getCreatedDate() == null) {

            System.out.println("Invalid trade record");

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid Trade request");

        }
        System.out.println("------------------------");
        return tradeService.createTrade(trade);
    }


    @RequestMapping(value="/trades", method=RequestMethod.GET)
    public List<Trade> readTrades() {
        return tradeService.getTrades();
    }



    @RequestMapping(value="/trades/{tradeId}", method=RequestMethod.GET)
    public List<Trade> readTradesbyId(@PathVariable(value = "tradeId") String id) {

        try {
            List<Trade> trades = tradeService.getTradesByTradeId(id);
            return trades;
        } catch (NoSuchElementException exception) {

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
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Trade Not Found");
            }

            return trade;
        } catch (NoSuchElementException exception) {

            System.out.println("Trade Not Found inside readTradesbyIdAndVersion");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Trade Not Found", exception);

        }


    }

    @RequestMapping(value="/trades/expired", method=RequestMethod.GET)
    public List<Trade> getExpiredTrades(@RequestParam(name = "expired") String expiredFlag) {

        System.out.println("Inside getExpiredTrades");

        return tradeService.getExpiredTrades();
    }

    @RequestMapping(value="/trades/expired", method=RequestMethod.PUT)
    public List<Trade> updateExpiredTrades(@RequestParam(name = "expired") String expiredFlag) {

        System.out.println("Inside updateExpiredTrades");

        return tradeService.updateExpiredTrades();
    }


    @RequestMapping(value="/trades/{tradeId}/{version}", method=RequestMethod.PUT)
    public Trade updateTrade(@PathVariable(value = "tradeId") String id,
                             @PathVariable(value = "version") int version,
                             @RequestBody Trade tradeDetails) {

        return tradeService.updateTrade(id, version, tradeDetails);
    }


    @RequestMapping(value="/trade/{tradeId}", method=RequestMethod.DELETE)
    public void deleteTrades(@PathVariable(value = "tradeId") String id) {
        tradeService.deleteTrade(id);
    }



}
