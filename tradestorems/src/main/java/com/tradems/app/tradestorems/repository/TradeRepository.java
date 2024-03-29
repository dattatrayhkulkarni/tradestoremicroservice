package com.tradems.app.tradestorems.repository;

import com.tradems.app.tradestorems.model.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TradeRepository extends JpaRepository<Trade, String> {

    Trade findByTradeIdAndVersion(String tradeId, int version );

    List<Trade> findByTradeId(String tradeId);

    @Modifying
    @Transactional
    void deleteByTradeIdAndVersion(String tradeId, int version);

    @Query(
            value = "SELECT * FROM trade t WHERE t.maturity_date < DATE(NOW())  AND expired= 'N'",
            nativeQuery = true)
    List<Trade> getTradesWithPreviousMaturityDate();

    @Modifying
    @Transactional
    @Query(
            value = "UPDATE trade  SET expired = 'Y' WHERE maturity_date < DATE(NOW())",
            nativeQuery = true)
    void updateTradesWithPreviousMaturityDate();

}
