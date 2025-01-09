package com.sathvik.portfolio_tracker.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public class StockAddDto {
    private int userId;
    private String ticker;
    private String stockName;

    @Min(value = 1, message = "Quantity must be greater than 0")
    @NotNull(message = "Quantity cannot be null")
    private double quantity;

    @NotNull(message = "Invested value cannot be null")
    @Min(value = 1, message = "Invested value must be greater than 0")
    private BigDecimal investedValue;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate purchaseDate;

    public StockAddDto() {
    }

    public StockAddDto(int userId, String ticker, String stockName, double quantity, BigDecimal investedValue, LocalDate purchaseDate) {
        this.userId = userId;
        this.ticker = ticker;
        this.stockName = stockName;
        this.quantity = quantity;
        this.investedValue = investedValue;
        this.purchaseDate = purchaseDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
}
