package com.fintech.task.dto;

import java.time.LocalDate;

public class CurrencyDto {
    private String currencyName;
    private String currencyBaseName;
    private double buy;
    private double sell;
    private String source;
    private LocalDate date;

    public CurrencyDto(String currencyName, String currencyBaseName, double buy, double sell,
                       String source, LocalDate date) {
        this.currencyName = currencyName;
        this.currencyBaseName = currencyBaseName;
        this.buy = buy;
        this.sell = sell;
        this.source = source;
        this.date = date;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencyBaseName() {
        return currencyBaseName;
    }

    public void setCurrencyBaseName(String currencyBaseName) {
        this.currencyBaseName = currencyBaseName;
    }

    public double getBuy() {
        return buy;
    }

    public void setBuy(double buy) {
        this.buy = buy;
    }

    public double getSell() {
        return sell;
    }

    public void setSell(double sell) {
        this.sell = sell;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
