package com.tradems.app.tradestorems.Integration;

import com.tradems.app.tradestorems.controller.TradeStoreController;
import com.tradems.app.tradestorems.model.Trade;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class IntegrationTests {


    @Autowired
    private TradeStoreController tradeStoreController;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void testCreateDelete() {
        String url = "http://localhost:"+port+"/api/trades";


        Trade trade1 = new Trade("T25", 13, "CP3", "B1",
                LocalDate.now(), LocalDate.now(), 'N');

        ResponseEntity<Trade> entity = restTemplate.postForEntity(url, trade1, Trade.class);

        Trade[] trades = restTemplate.getForObject(url, Trade[].class);
        Assertions.assertThat(trades).extracting(Trade::getTradeId).contains("T25");

        restTemplate.delete(url + "/" + entity.getBody().getTradeId() + "/"+ entity.getBody().getVersion());
        
    }





}
