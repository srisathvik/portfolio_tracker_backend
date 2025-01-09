package com.sathvik.portfolio_tracker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int portfolioId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;
    private double  quantity;
    @Column(name = "buy_price")
    private BigDecimal buyPrice;

    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    public Portfolio() {
    }

    public Portfolio(int portfolioId, User user, Stock stock, double quantity, BigDecimal buyPrice) {
        this.portfolioId = portfolioId;
        this.user = user;
        this.stock = stock;
        this.quantity = quantity;
        this.buyPrice = buyPrice;
    }

    public Portfolio(User user, Stock stock, double quantity, BigDecimal buyPrice, LocalDate purchaseDate) {
        this.user = user;
        this.stock = stock;
        this.quantity = quantity;
        this.buyPrice = buyPrice;
        this.purchaseDate = purchaseDate;
    }

    public int getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(int portfolioId) {
        this.portfolioId = portfolioId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
}
