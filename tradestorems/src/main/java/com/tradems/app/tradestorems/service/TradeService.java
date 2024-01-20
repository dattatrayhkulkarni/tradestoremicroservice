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

    public void deleteTrade(String tradeId) {
        tradeRepository.deleteById(tradeId);
    }

     public Trade getTradesById(String tradeId, int version) {

        Trade trade = tradeRepository.findByTradeIdAndVersion(tradeId,version);

        if (trade == null) {
            System.out.println("*** Trade is not found ");
        }
        return trade;

    }

    public Trade getTradesByIdVersion(String tradeId, int version) {

        //findByNameAndLocation(String name, String location)

        Trade trade = tradeRepository.findByTradeIdAndVersion(tradeId, version);

       // Trade trade = tradeRepository.findById(tradeId).get();

        if (trade == null) {
            System.out.println("*** Trade is not found ");
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



    // UPDATE
    public Trade updateTrade(String tradeId, Trade tradeDetails) {
        Trade trade = tradeRepository.findById(tradeId).get();

        trade.setBookId(tradeDetails.getBookId());
        trade.setExpired(tradeDetails.getExpired());
        trade.setVersion(tradeDetails.getVersion());
        trade.setCreatedDate(tradeDetails.getCreatedDate());
        trade.setMaturityDate(tradeDetails.getMaturityDate());
        trade.setCounterPartyId(tradeDetails.getCounterPartyId());

        return tradeRepository.save(trade);
    }


}
