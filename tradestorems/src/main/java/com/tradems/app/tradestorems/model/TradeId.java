package com.tradems.app.tradestorems.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class TradeId  implements Serializable {

    private String tradeId;
    private int version;

    public TradeId(String tradeId, int version) {
        this.tradeId = tradeId;
        this.version = version;
    }

    public TradeId() {
    }
}
