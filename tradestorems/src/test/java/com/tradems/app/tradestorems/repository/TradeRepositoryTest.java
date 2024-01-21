package com.tradems.app.tradestorems.repository;

import com.tradems.app.tradestorems.model.Trade;
import com.tradems.app.tradestorems.service.TradeService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@SpringBootTest
class TradeRepositoryTest {

    @Autowired
    private TradeRepository tradetRepository;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void createTrade() throws Exception{

        Trade trade = new Trade("T2", 8, "CP3", "B1",
                LocalDate.now(), LocalDate.now(), 'N');

        tradetRepository.save(trade);
        ResultActions response;

        response = mockMvc.perform(MockMvcRequestBuilders.get("/api/trades/T2"));
        response.andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    void createTradeWithDifferentVersions() throws Exception{

        Trade trade1 = new Trade("T22", 8, "CP3", "B1",
                LocalDate.now(), LocalDate.now(), 'N');

        Trade trade2 = new Trade("T22", 9, "CP3", "B1",
                LocalDate.now(), LocalDate.now(), 'N');

        tradetRepository.save(trade1);
        tradetRepository.save(trade2);
        ResultActions response;

        response = mockMvc.perform(MockMvcRequestBuilders.get("/api/trades/T22"));
        response.andExpect(MockMvcResultMatchers.status().isOk());

        response.andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(2)));

    }


    @Test
    void createTradeWithPreviousMaturity(){

        Trade trade = new Trade("T2", 8, "CP3", "B1",
                LocalDate.now().minusDays(1), LocalDate.now(), 'N');


        TradeService tradeService = new TradeService();

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> tradeService.createTrade(trade),
                "Invalid Trade request"
        );

        assertTrue(exception.getMessage().contains("Invalid Trade request"));

    }

    @Test
    void testUpdateTrade(){

        Trade trade = new Trade("T2", 10, "CP3", "B1",
                LocalDate.now(), LocalDate.now(), 'N');

        tradetRepository.save(trade);

        String newBookId = "Updated B1";
        trade.setBookId(newBookId);

        tradetRepository.save(trade);

        Trade updatedTrade = tradetRepository.findByTradeIdAndVersion("T2", 10);

        assertEquals(newBookId, updatedTrade.getBookId());

    }



}