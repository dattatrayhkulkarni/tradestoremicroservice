package com.tradems.app.tradestorems.service;


import com.tradems.app.tradestorems.model.Trade;
import com.tradems.app.tradestorems.repository.TradeRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
class TradeServiceTest {

    @Mock
    private TradeRepository tradeRepository;

    @InjectMocks
    private TradeService tradeService;

    @Test
    public void createTrade() {

        Trade trade = new Trade("T2", 13, "CP3", "B1",
                LocalDate.now(), LocalDate.now(), 'N');

        tradeService.createTrade(trade);

    }

    @Test
    public void getTrades() {

    }

}