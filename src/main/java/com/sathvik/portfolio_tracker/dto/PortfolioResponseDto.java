package com.sathvik.portfolio_tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

public class PortfolioResponseDto {
    private int userId;
    private String name;
    private BigDecimal investedValue;
    private BigDecimal currentValue;
    private BigDecimal profitLoss;
    private BigDecimal percentageChange;

    private String errorMessage;

    public PortfolioResponseDto() {
    }

    public PortfolioResponseDto(int userId, String name, BigDecimal investedValue, BigDecimal currentValue, BigDecimal profitLoss, BigDecimal percentageChange, String errorMessage) {
        this.userId = userId;
        this.name = name;
        this.investedValue = investedValue;
        this.currentValue = currentValue;
        this.profitLoss = profitLoss;
        this.percentageChange = percentageChange;
        this.errorMessage = errorMessage;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getInvestedValue() {
        return investedValue;
    }

    public void setInvestedValue(BigDecimal investedValue) {
        this.investedValue = investedValue;
    }

    public BigDecimal getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(BigDecimal currentValue) {
        this.currentValue = currentValue;
    }

    public BigDecimal getProfitLoss() {
        return profitLoss;
    }

    public void setProfitLoss(BigDecimal profitLoss) {
        this.profitLoss = profitLoss;
    }

    public BigDecimal getPercentageChange() {
        return percentageChange;
    }

    public void setPercentageChange(BigDecimal percentageChange) {
        this.percentageChange = percentageChange;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
