package com.sathvik.portfolio_tracker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int stockId;

    @Column(name = "ticker", nullable = false)
    private String ticker;

    @Column(name = "stock_name", nullable = false)
    private String StockName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stock")
    private Set<Portfolio> portfolios;

    public Stock() {
    }

    public Stock(int stockId, String ticker, String stockName, Set<Portfolio> portfolios) {
        this.stockId = stockId;
        this.ticker = ticker;
        StockName = stockName;
        this.portfolios = portfolios;
    }

    public Stock(String ticker, String stockName) {
        this.ticker = ticker;
        StockName = stockName;
    }

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getStockName() {
        return StockName;
    }

    public void setStockName(String stockName) {
        StockName = stockName;
    }

    public Set<Portfolio> getPortfolios() {
        return portfolios;
    }

    public void setPortfolios(Set<Portfolio> portfolios) {
        this.portfolios = portfolios;
    }
}
