package com.fintech.task.dto;

import com.fintech.task.service.impl.CurrencyServiceMinfin;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CurrencyMinfinDto {
    private Long id;
    private String pointDate;
    private String date;
    private double ask;
    private double bid;
    private double trendAsk;
    private double trendBid;
    private String currency;

    public CurrencyMinfinDto() {}

    public CurrencyMinfinDto(Long id, String pointDate, String date, double ask,
                             double bid, double trendAsk, double trendBid, String currency) {
        this.id = id;
        this.pointDate = pointDate;
        this.date = date;
        this.ask = ask;
        this.bid = bid;
        this.trendAsk = trendAsk;
        this.trendBid = trendBid;
        this.currency = currency;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPointDate() {
        return pointDate;
    }

    public void setPointDate(String pointDate) {
        this.pointDate = pointDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getAsk() {
        return ask;
    }

    public void setAsk(double ask) {
        this.ask = ask;
    }

    public double getBid() {
        return bid;
    }

    public void setBid(double bid) {
        this.bid = bid;
    }

    public double getTrendAsk() {
        return trendAsk;
    }

    public void setTrendAsk(double trendAsk) {
        this.trendAsk = trendAsk;
    }

    public double getTrendBid() {
        return trendBid;
    }

    public void setTrendBid(double trendBid) {
        this.trendBid = trendBid;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
