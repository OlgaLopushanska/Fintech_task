package com.fintech.task.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "currencies")
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String currencyName;

    @Column
    private String baseCurrencyName;
    @Column
    private double buy;
    @Column
    private double sell;
    @Column
    private LocalDate date;
    @Column
    private String source;

    public Currency() {}

    public Currency(Long id, String currencyName, String baseCurrencyName, double buy,
                    double sell, LocalDate date, String source) {
        this.id = id;
        this.currencyName = currencyName;
        this.baseCurrencyName = baseCurrencyName;
        this.buy = buy;
        this.sell = sell;
        this.date = date;
        this.source = source;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getBaseCurrencyName() {
        return baseCurrencyName;
    }

    public void setBaseCurrencyName(String baseCurrencyName) {
        this.baseCurrencyName = baseCurrencyName;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
