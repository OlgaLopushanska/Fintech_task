package com.fintech.task.dto;

import jakarta.persistence.criteria.CriteriaBuilder;

public class CurrencyMonoDto {
    private Long id;
    private Integer currencyCodeA;
    private Integer currencyCodeB;
    private Long date;
    private double rateBuy;
    private double rateCross;
    private double rateSell;

    public CurrencyMonoDto(Long id, Integer currencyCodeA, Integer currencyCodeB,
                           Long date, double rateBuy, double rateCross, double rateSell) {
        this.id = id;
        this.currencyCodeA = currencyCodeA;
        this.currencyCodeB = currencyCodeB;
        this.date = date;
        this.rateBuy = rateBuy;
        this.rateCross = rateCross;
        this.rateSell = rateSell;
    }
    public CurrencyMonoDto() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCurrencyCodeA() {
        return currencyCodeA;
    }

    public void setCurrencyCodeA(Integer currencyCodeA) {
        this.currencyCodeA = currencyCodeA;
    }

    public Integer getCurrencyCodeB() {
        return currencyCodeB;
    }

    public void setCurrencyCodeB(Integer currencyCodeB) {
        this.currencyCodeB = currencyCodeB;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public double getRateBuy() {
        return rateBuy;
    }

    public void setRateBuy(double rateBuy) {
        this.rateBuy = rateBuy;
    }

    public double getRateCross() {
        return rateCross;
    }

    public void setRateCross(double rateCross) {
        this.rateCross = rateCross;
    }

    public double getRateSell() {
        return rateSell;
    }

    public void setRateSell(double rateSell) {
        this.rateSell = rateSell;
    }
}
