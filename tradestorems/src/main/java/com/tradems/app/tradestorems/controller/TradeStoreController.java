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




    /*
    @GetMapping("/{id}")
    public Trade getUserById(@PathVariable("/trades/{tradeId}") String id) {

        Optional<Trade> trade = tradeService.getTradesById(id);

        if (trade.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User with id %d not found", id));
        }

        return trade;
    }
    */


    // java.util.NoSuchElementException


    @RequestMapping(value="/trades/{tradeId}", method=RequestMethod.PUT)
    public Trade updateTrade(@PathVariable(value = "tradeId") String id, @RequestBody Trade tradeDetails) {
        return tradeService.updateTrade(id, tradeDetails);
    }

    @RequestMapping(value="/trade/{tradeId}", method=RequestMethod.DELETE)
    public void deleteTrades(@PathVariable(value = "tradeId") String id) {
        tradeService.deleteTrade(id);
    }



}
