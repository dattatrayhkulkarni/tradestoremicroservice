package com.tradems.app.tradestorems.service;

import com.tradems.app.tradestorems.model.Trade;
import com.tradems.app.tradestorems.repository.TradeRepository;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

//import static com.sun.org.apache.xerces.internal.util.PropertyState.is;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@SpringBootTest
class TradeServiceTest {

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

    /*

    @Test
    void createTradeWithLowerVersion(){

        Trade trade1 = new Trade("T33", 8, "CP3", "B1",
                LocalDate.now().minusDays(1), LocalDate.now(), 'N');

        tradetRepository.save(trade1);

        Trade trade2 = new Trade("T33", 7, "CP3", "B1",
                LocalDate.now().minusDays(1), LocalDate.now(), 'N');


        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> tradetRepository.save(trade2),
                "Trade with Lower version received"
        );

        assertTrue(exception.getMessage().contains("Trade with Lower version received"));

    }

*/



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
    void getTradesById() {
    }

    @Test
    void testCreateTrade() {
    }

    @Test
    void testGetTradesById() {
    }
}