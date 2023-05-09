package com.fintech.task.dto;

import java.util.List;

public class CurrencyPrivatArchiveDto {
    private String date;
    private String bank;
    private int baseCurrency;
    private String baseCurrencyLit;
    private List<CurrencyBasePrivatArchiveDto> exchangeRate;

    public CurrencyPrivatArchiveDto(String date, String bank, int baseCurrency,
                                    String baseCurrencyLit, List<CurrencyBasePrivatArchiveDto> exchangeRate) {
        this.date = date;
        this.bank = bank;
        this.baseCurrency = baseCurrency;
        this.baseCurrencyLit = baseCurrencyLit;
        this.exchangeRate = exchangeRate;
    }
    public CurrencyPrivatArchiveDto() {}
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public int getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(int baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public String getBaseCurrencyLit() {
        return baseCurrencyLit;
    }

    public void setBaseCurrencyLit(String baseCurrencyLit) {
        this.baseCurrencyLit = baseCurrencyLit;
    }

    public List<CurrencyBasePrivatArchiveDto> getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(List<CurrencyBasePrivatArchiveDto> exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
}
