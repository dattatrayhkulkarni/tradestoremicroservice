package com.tradems.app.tradestorems.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tradems.app.tradestorems.model.Trade;
import com.tradems.app.tradestorems.service.TradeService;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;


import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(TradeStoreController.class)
class TradeStoreControllerTest {

    @MockBean
    TradeService tradeService;

    @Autowired
    MockMvc mockMvc;


    @Test
    public void testfindAll() throws Exception {

        Trade trade1 = new Trade("T22", 8, "CP3", "B1",
                LocalDate.now(), LocalDate.now(), 'N');

        Trade trade2 = new Trade("T22", 9, "CP3", "B1",
                LocalDate.now(), LocalDate.now(), 'N');

        List<Trade> trades = Arrays.asList(trade1,trade2);

        Mockito.when(tradeService.getTrades()).thenReturn(trades);

        ResultActions response;

        response = mockMvc.perform(MockMvcRequestBuilders.get("/api/trades"));
        response.andExpect(MockMvcResultMatchers.status().isOk());
        response.andExpect(jsonPath("$.size()", CoreMatchers.is(2)));

    }


    @Test
    public void testfindByTradeid() throws Exception {

        Trade trade1 = new Trade("T22", 8, "CP3", "B1",
                LocalDate.now(), LocalDate.now(), 'N');

        Trade trade2 = new Trade("T22", 9, "CP3", "B1",
                LocalDate.now(), LocalDate.now(), 'N');

        Trade trade3 = new Trade("T22", 10, "CP3", "B1",
                LocalDate.now(), LocalDate.now(), 'N');

        List<Trade> trades = Arrays.asList(trade1,trade2,trade3);

        Mockito.when(tradeService.getTradesByTradeId("T22")).thenReturn(trades);

        ResultActions response;

        response = mockMvc.perform(MockMvcRequestBuilders.get("/api/trades/T22"));
        response.andExpect(MockMvcResultMatchers.status().isOk());
        response.andExpect(jsonPath("$.size()", CoreMatchers.is(3)));

    }


    @Test
    public void testExpiredTrades() throws Exception {


        Trade trade3 = new Trade("T22", 10, "CP3", "B1",
                LocalDate.now().minusDays(1), LocalDate.now(), 'N');

        Trade trade4 = new Trade("T22", 11, "CP3", "B1",
                LocalDate.now(), LocalDate.now().minusDays(1), 'N');



        List<Trade> expiredTrades = Arrays.asList(trade3,trade4);


        Mockito.when(tradeService.getExpiredTrades()).thenReturn(expiredTrades);

        ResultActions response;

        response = mockMvc.perform(MockMvcRequestBuilders.get("/api/trades/expired?expired=true"));
        response.andExpect(MockMvcResultMatchers.status().isOk());
        response.andExpect(jsonPath("$.size()", CoreMatchers.is(2)));

    }




    @Test
    public void testCreateTrade() throws Exception {

        Trade trade1 = new Trade("T22", 8, "CP3", "B1",
                LocalDate.now(), LocalDate.now(), 'N');

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        Mockito.when(tradeService.createTrade(trade1)).thenReturn(trade1);

        String json = mapper.writeValueAsString(trade1);

        ResultActions response;

        response = mockMvc.perform(MockMvcRequestBuilders.post("/api/trades").contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());

    }



    @Test
    public void testUpdateTrade() throws Exception {

        Trade trade1 = new Trade("T22", 8, "CP3", "B1",
                LocalDate.now(), LocalDate.now(), 'N');

        Mockito.when(tradeService.updateTrade(trade1.getTradeId(), trade1.getVersion(),trade1)).thenReturn(trade1);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        trade1.setBookId("Updated B1");

        String json = mapper.writeValueAsString(trade1);

        ResultActions response;

        response = mockMvc.perform(MockMvcRequestBuilders.put("/api/trades/T22/8").contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void testfindByTradeidAndVersion() throws Exception {

        Trade trade1 = new Trade("T22", 15, "CP3", "B1",
                LocalDate.now(), LocalDate.now(), 'N');

        Mockito.when(tradeService.getTradesByIdVersion("T22", 15)).thenReturn(trade1);

        ResultActions response;

        response = mockMvc.perform(MockMvcRequestBuilders.get("/api/trades/T22/15"));
        response.andExpect(MockMvcResultMatchers.status().isOk());

    }







}