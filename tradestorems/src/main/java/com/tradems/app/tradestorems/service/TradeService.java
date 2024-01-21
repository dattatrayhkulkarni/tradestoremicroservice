package com.tradems.app.tradestorems.service;

import com.tradems.app.tradestorems.model.Trade;
import com.tradems.app.tradestorems.model.TradeId;
import com.tradems.app.tradestorems.repository.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class TradeService {

    @Autowired
    TradeRepository tradeRepository;

    public Trade createTrade(Trade trade) {

        System.out.println("Inside Create Trade ");
        System.out.println("Value of trade = ");
        System.out.println(trade);

        if(trade.getMaturityDate().isBefore(LocalDate.now())) {
            System.out.println("trade with earlier Maturity date received, ignoring ");
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid Trade request");
        }


        List<Trade> tradeList = getTradesByTradeId(trade.getTradeId());
        // check if Trade with lower version is received

        for (Trade tradeEntry: tradeList) {

            if(trade.getVersion() < tradeEntry.getVersion()) {
                System.out.println("trade with lower version received, throwing an exception");
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Trade with Lower version received");
            }

        }


        Trade tradeEntry = getTradesByIdVersion(trade.getTradeId(),trade.getVersion());


        if(tradeEntry == null) {
            System.out.println("trade entry not found ");
        } else {
            System.out.println("trade entry found for tradeId " +
                    trade.getTradeId() + " and version = " + trade.getVersion());
        }

        return tradeRepository.save(trade);
    }


    public List<Trade> getTrades() {

        return tradeRepository.findAll();
    }

    public void deleteTrade(String tradeId, int version) {
        tradeRepository.deleteByTradeIdAndVersion(tradeId, version);
    }

     public Trade getTradesById(String tradeId, int version) {

        Trade trade = tradeRepository.findByTradeIdAndVersion(tradeId,version);

        if (trade == null) {
            System.out.println("*** Trade is not found ");
        }
        return trade;

    }

    public Trade getTradesByIdVersion(String tradeId, int version) {

        Trade trade = tradeRepository.findByTradeIdAndVersion(tradeId, version);

        if (trade == null) {
            System.out.println("*** Trade with given TradeId and Version not Found ");
        }
        return trade;

    }

    public List<Trade> getTradesByTradeId(String tradeId) {

        List<Trade> trades = tradeRepository.findByTradeId(tradeId);

        if (trades == null) {
            System.out.println("*** Trade is not found ");


        }
        return trades;

    }


    public List<Trade> getExpiredTrades() {

        List<Trade> trades = tradeRepository.getTradesWithPreviousMaturityDate();

        if (trades == null) {
            System.out.println("*** No Trades with previous maturity date ");


        }
        return trades;
    }


    public List<Trade> updateExpiredTrades() {

        List<Trade> trades = tradeRepository.getTradesWithPreviousMaturityDate();

        if (trades == null) {
            System.out.println("*** No Trades with previous maturity date ");
        }

        tradeRepository.updateTradesWithPreviousMaturityDate();
        return trades;
    }


        // UPDATE
    public Trade updateTrade(String tradeId, int version, Trade tradeDetails) {

        Trade trade = tradeRepository.findByTradeIdAndVersion(tradeId, version);

        if(trade == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Trade with given Trade Id and version not found");
        }

        trade.setBookId(tradeDetails.getBookId());
        trade.setExpired(tradeDetails.getExpired());
        trade.setCreatedDate(tradeDetails.getCreatedDate());
        trade.setMaturityDate(tradeDetails.getMaturityDate());
        trade.setCounterPartyId(tradeDetails.getCounterPartyId());

        return tradeRepository.save(trade);
    }


}
