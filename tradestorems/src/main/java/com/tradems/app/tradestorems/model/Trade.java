package com.tradems.app.tradestorems.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "trade")
@IdClass(TradeId.class)
public class Trade {

    @Id
    @Column(name="trade_id", nullable = false)
    @JsonProperty("trade_id")
    private String tradeId;


    @Id
    @Column(name="version")
    @JsonProperty("version")
    private int version;

    @Column(name="counter_party_id")
    @JsonProperty("counter_party_id")
    private String counterPartyId;

    @Column(name="book_id")
    @JsonProperty("book_id")
    private String bookId;

    @Column(name="maturity_date")
    @JsonProperty("maturity_date")
    private LocalDate maturityDate;

    @Column(name="created_date")
    @JsonProperty("created_date")
    private LocalDate createdDate;

    @Column(name="expired")
    @JsonProperty("expired")
    private char expired;

    public Trade() {
    }

    public Trade(String tradeId, int version, String counterPartyId, String bookId,
                 LocalDate maturityDate,
                 LocalDate createdDate, char expired) {
        this.tradeId = tradeId;
        this.version = version;
        this.counterPartyId = counterPartyId;
        this.bookId = bookId;
        this.maturityDate = maturityDate;
        this.createdDate = createdDate;
        this.expired = expired;
    }


    public String getTradeId() {
        return tradeId;
    }

    public int getVersion() {
        return version;
    }

    public String getCounterPartyId() {
        return counterPartyId;
    }

    public String getBookId() {
        return bookId;
    }

    public LocalDate getMaturityDate() {
        return maturityDate;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public char getExpired() {
        return expired;
    }


    public void setVersion(int version) {
        this.version = version;
    }

    public void setCounterPartyId(String counterPartyId) {
        this.counterPartyId = counterPartyId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public void setMaturityDate(LocalDate maturityDate) {
        this.maturityDate = maturityDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public void setExpired(char expired) {
        this.expired = expired;
    }

    @Override
    public String toString() {
        return "Trade{" +
                "tradeId='" + tradeId + '\'' +
                ", version=" + version +
                ", counterPartyId='" + counterPartyId + '\'' +
                ", bookId='" + bookId + '\'' +
                ", maturityDate=" + maturityDate +
                ", createdDate=" + createdDate +
                ", expired=" + expired +
                '}';
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }
}
