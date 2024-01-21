package com.tradems.app.tradestorems.repository;

import com.tradems.app.tradestorems.model.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeRepository extends JpaRepository<Trade, String> {

    Trade findByTradeIdAndVersion(String tradeId, int version );

    List<Trade> findByTradeId(String tradeId);

    @Query(
            value = "SELECT * FROM trade t WHERE t.maturity_date < DATE(NOW()))",
            nativeQuery = true)
    List<Trade> getTradesWithPreviousMaturityDate();

}
