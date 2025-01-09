package com.sathvik.portfolio_tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

public class StockPortfolioDto {

    private String ticker;
    private String stockName;
    private double quantity;
    private BigDecimal investedValue;

    public StockPortfolioDto(String ticker, double quantity, BigDecimal investedValue) {
        this.ticker = ticker;
        this.quantity = quantity;
        this.investedValue = investedValue;
    }

    public StockPortfolioDto() {
    }

    public StockPortfolioDto(String ticker, String stockName, double quantity, BigDecimal investedValue) {
        this.ticker = ticker;
        this.stockName = stockName;
        this.quantity = quantity;
        this.investedValue = investedValue;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getInvestedValue() {
        return investedValue;
    }

    public void setInvestedValue(BigDecimal investedValue) {
        this.investedValue = investedValue;
    }
}
