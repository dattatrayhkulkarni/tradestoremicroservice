package com.tradems.app.tradestorems.service;


import com.tradems.app.tradestorems.model.Trade;
import com.tradems.app.tradestorems.repository.TradeRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TradeServiceTest {

    @Mock
    private TradeRepository tradeRepository;

    @InjectMocks
    private TradeService tradeService;

    @Test
    public void testCreateTrade() {

        Trade trade = new Trade("T25", 13, "CP3", "B1",
                LocalDate.now(), LocalDate.now(), 'N');

        given(tradeRepository.save(trade)).willReturn(trade);

        Trade createdTrade = tradeService.createTrade(trade);

        assertThat(createdTrade).isNotNull();


    }



    @Test
    public void testGetTrades() {

        Trade trade1 = new Trade("T25", 13, "CP3", "B1",
                LocalDate.now(), LocalDate.now(), 'N');

        Trade trade2 = new Trade("T25", 14, "CP3", "B1",
                LocalDate.now(), LocalDate.now(), 'N');

        Trade trade3 = new Trade("T25", 16, "CP3", "B1",
                LocalDate.now(), LocalDate.now(), 'N');

        List<Trade> trades = Arrays.asList(trade1, trade2, trade3);

        given(tradeRepository.findAll()).willReturn(trades);

        List<Trade> searchedTrades = tradeService.getTrades();

        assertThat(searchedTrades).isNotNull();


    }

    @Test
    public void testGetTradesById() {

        Trade trade1 = new Trade("T25", 13, "CP3", "B1",
                LocalDate.now(), LocalDate.now(), 'N');

        Trade trade2 = new Trade("T25", 14, "CP3", "B1",
                LocalDate.now(), LocalDate.now(), 'N');

        Trade trade3 = new Trade("T25", 16, "CP3", "B1",
                LocalDate.now(), LocalDate.now(), 'N');

        List<Trade> trades = Arrays.asList(trade1, trade2, trade3);

        given(tradeRepository.findByTradeId("T25")).willReturn(trades);

        List<Trade> searchedTrades = tradeService.getTradesByTradeId("T25");

        assertThat(searchedTrades).hasSize(3);


    }


}