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
        response.andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(2)));

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
        response.andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(3)));

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


}