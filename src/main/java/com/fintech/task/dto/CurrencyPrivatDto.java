package com.fintech.task.dto;


public class CurrencyPrivatDto {
    private Long id;
    private String ccy;
    private String base_ccy;
    private double buy;
    private double sale;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCcy() {
        return ccy;
    }

    public void setCcy(String ccy) {
        this.ccy = ccy;
    }

    public String getBase_ccy() {
        return base_ccy;
    }

    public void setBase_ccy(String base_ccy) {
        this.base_ccy = base_ccy;
    }

    public double getBuy() {
        return buy;
    }

    public void setBuy(double buy) {
        this.buy = buy;
    }

    public double getSale() {
        return sale;
    }

    public void setSale(double sale) {
        this.sale = sale;
    }
    public CurrencyPrivatDto() {}
    public CurrencyPrivatDto(Long id, String ccy, String base_ccy, double buy, double sale) {
        this.id = id;
        this.ccy = ccy;
        this.base_ccy = base_ccy;
        this.buy = buy;
        this.sale = sale;
    }
}
